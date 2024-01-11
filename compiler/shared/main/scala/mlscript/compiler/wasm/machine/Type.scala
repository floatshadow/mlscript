package mlscript.compiler.wasm

import mlscript.compiler.ClassInfo
import mlscript.compiler.{ClsKind, TraitKind}
import mlscript.utils.*
import mlscript.utils.shorthands.*

import scala.collection.BitSet
import scala.collection.immutable.{List, Map, VectorMap}
import scala.collection.mutable.ListBuffer

case class MethodInfo(
  clsName: Str, // this cls identify the actual source class of this method
  methodName: Str,
  fid: Int,
  isVirtual: Bool
)


enum ClassMember:
  case ClassField(offset: Int)
  case ClassMethod(method: MethodInfo)
  case TraitMethod(traitName: Str, method: MethodInfo)

// mlscript class is encoded as a record with some field
// handle memory layout
class RecordObj(
  fields: Ls[(String, Type)], 
  methods: Map[String, MethodInfo],
  traits: Ls[(ClassInfo, Map[String, MethodInfo])]
):
  import ClassMember.*
  import Symbol.*

  def buildVtable() =
    methods.toList.filter(m => m._2.isVirtual)

  lazy val vtable: Ls[(String, MethodInfo)] = buildVtable()
  def getVtable = this.vtable

  def hasVirtual = this.vtable.nonEmpty

  def getVtableIndex(name: Str): Int =
    vtable.indexWhere {_._1 == name}

  def getItable =
    traits map {
      case (name, methods) => (name, methods.toList)
    }
    
  //===-- search members  ===//
  private def containsField(name: Str): Bool =
    // assume params, fields have no name conflict
    fields.toMap.contains(name)

  private def containsMethod(name: Str): Bool =
    methods.contains(name)

  private def getMethod(name: Str): MethodInfo =
    methods(name)
  
  private def searchTraitMethod(name: Str): Opt[ClassMember] =
    val search = traits collect {
      case (traitName, traitMethods) =>
        traitMethods.get(name) match
          case S(impl) => (traitName, impl)        
    }
    if search.size > 1 then
      throw WasmBackendError(s"try to invoke $name, but find multiple candidate implementation")
    
    if search.size == 1 then
      S(TraitMethod(search.head._1.ident, search.head._2))
    else
      None

  def getMemberUniverse(name: Str): Opt[ClassMember] =
    if containsMethod(name) then
      S(ClassMethod(getMethod(name)))
    else if containsField(name) then
      S(ClassField(getFieldOffset(name)))
    else 
      searchTraitMethod(name)
  
  //===-- compute layout information ===//
  def size: Int =
    val pvtb = if hasVirtual then RecordObj.defaultFieldSize else 0
    RecordObj.headerSize + pvtb + fields.size * RecordObj.defaultFieldSize

  def getFieldOffset(name: String): Int =
    val index = fields.indexWhere { _._1 == name }
    val pvtb = if hasVirtual then RecordObj.defaultFieldSize else 0
    RecordObj.headerSize + pvtb + index * RecordObj.defaultFieldSize
  
  // ad hoc overload only for opaque record e.g. LetCall
  def getFieldOffset(index: Int) : Int =
    RecordObj.headerSize + index * RecordObj.defaultFieldSize
  
  def getPVtableOffset: Int =
    RecordObj.headerSize
  
  def getPItableOffset: Int =
    RecordObj.headerSize + RecordObj.pvtableSize
  
  def getParentOffet(name: Str): Int =
    // in our case class have at most 1 parent.
    // its members are decomposed into scalar and
    // placed at the beginning of the class
    0
  
  override def toString: String =
    f"{fields: (${fields.toMap.keys.mkString(",")}), methods: (${methods.keys.mkString(",")}), vtable: (${vtable.map(_._1) mkString ","})}\n"


object RecordObj:
  final val headerSize = 4
  final val pvtableSize = 4
  final val itableSize = 4
  final val defaultFieldSize = 4

  // assume trait contains only methods
  private def collectField(clsctx: Map[Str, ClassInfo], cls: ClassInfo): Ls[(String, Type)] =
    def collectFieldRec(wcls: ClassInfo): Ls[Str] =
      val params = wcls.fields
      val fields = wcls.members.keys.toList
      val base = wcls.parents.keys.toList flatMap { 
        pname =>
          val pcls = clsctx(pname)
          pcls.kind match
            case ClsKind =>
              collectFieldRec(pcls)
            case _ =>
              Ls()
      }
      base ++ params ++ fields
    val fieldNames = collectFieldRec(cls)
    val numFields = fieldNames.size
    fieldNames.zip(List.fill(numFields)(Type.defaultNumType))

  private def collectMethod(clsctx: Map[Str, ClassInfo], cls: ClassInfo) =
    def collectMethodRec(wcls: ClassInfo): Ls[(Str, MethodInfo)] =
      // scala's default duplicate handling policy shows
      // duplicate keys will be overwritten by later keys
      val methods = wcls.methods.toList map {
        case (name, mdef) =>
          name ->
          MethodInfo(
            wcls.ident,
            name,
            mdef.id,
            mdef.isVirtual,
          )
      }
      val base = wcls.parents.keys.toList flatMap {
        pname =>
          val pcls = clsctx(pname)
          pcls.kind match
            case ClsKind =>
              collectMethodRec(pcls)
            case _ =>
              Ls()
      }
      base ++ methods
    collectMethodRec(cls).toMap
  
  // decouple implemented trait methods from methods lists,
  // it returns class defined methods and full lists of implemented/inherited trait methods
  private def decoupleImplFromMethod(
    traits: Ls[(ClassInfo, Map[Str, MethodInfo])], 
    methods: Map[Str, MethodInfo]
  ) =
    var implCtx = Set[Str]()
    val implTrait = traits map {
      case (traitCls, traitMethods) =>
        val implMethods = traitMethods map {
          case (name, minfo) =>
            methods.get(name) match
              case S(impl) =>
                implCtx = implCtx + name
                (name, impl)
              case None =>
                (name, minfo)
        }
        traitCls -> implMethods
    }
    val classMethods = methods filter { case (name, _) => !implCtx.contains(name) }
    (implTrait, classMethods)

  private def collectTrait(clsctx: Map[Str, ClassInfo], cls: ClassInfo) =
    def collectTraitRec(wcls: ClassInfo): Ls[(ClassInfo, Map[Str, MethodInfo])] =
      val traits = wcls.parents.keys.toList flatMap {
        pname =>
          val pcls = clsctx(pname)
          collectTraitRec(pcls)
      }
      // TODO: do this more efficiently
      val methods = collectMethod(clsctx, wcls)
      val (implTrait, classMethods) = decoupleImplFromMethod(traits, methods)
      wcls.kind match
        case ClsKind => implTrait
        case TraitKind => (wcls, classMethods) +: implTrait

    collectTraitRec(cls)

  def from(clsctx: Map[Str, ClassInfo], cls: ClassInfo) =
    val fieldsLayout = collectField(clsctx, cls)
    val methodLayout = collectMethod(clsctx, cls)
    val traitsLayout = collectTrait(clsctx, cls)
    val (implTrait, classMethods) = decoupleImplFromMethod(traitsLayout, methodLayout)
    val layoutAux = RecordObj(fieldsLayout, classMethods, implTrait)
    layoutAux

  def opaque(numFields: Int) =
    val fields = List.range(0, numFields) map { index => (index.toString() -> Type.defaultNumType)}
    RecordObj(fields, Map(), Ls())

class VariantObj(val variants: Map[String, Option[Type.Record]]):
  def apply(name: String): Option[Option[Type.Record]] = variants.get(name)
  // Pattern to check if the variant is of a certain type
  // The first element is the offset in bits, the second element is
  // the bit pattern. offset + bit pattern length = record offset,
  // the third element is a list of forbidden patterns.
  def checkBits(name: String): (Int, BitSet, List[BitSet]) = ???

  override def toString(): String = "<" + variants
    .map((name, ty) => name + ty.map(rec => s": $rec").getOrElse(""))
    .mkString(" | ") + ">"


sealed trait ValueType:
  override def toString(): String
  def show: String

// TODO: support all wasm types
enum Type:
  case Void
  case Int32 extends Type, ValueType
  case Int64 extends Type, ValueType
  case Float32 extends Type, ValueType
  case Float64 extends Type, ValueType
  case Record(impl: ClassInfo)
  case FuncType(params: Ls[Type], ret: Ls[Type])

  override def toString(): String = show

  def show: String = 
    toDocument.print
  
  def toDocument: Document = this match
    case Void =>  "" |> raw
    case Int32 => "i32" |> raw
    case Int64 => "i64" |> raw
    case Float32 => "f32" |> raw
    case Float64 => "f64" |> raw
    case Record(classinfo) => 
      raw(classinfo.ident)
      <#> raw("(") <#> raw(classinfo.fields map {x => x.toString()} mkString(","))
      <#> raw(")")
    case FuncType(params, ret) =>
      raw("(func ") <#>
      line(params.map {x => raw(s"(param $x)")}) <:>
      line(ret.map {x => raw(s"(result $x)")}) <#>
      raw(")")

object Type:
  // TODO: GraphOptimizer does not support type 
  final val defaultNumType: Type = Int32