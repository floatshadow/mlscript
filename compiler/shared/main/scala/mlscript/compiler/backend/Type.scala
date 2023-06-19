package mlscript.compiler.backend

import scala.collection.BitSet
import scala.collection.mutable.Map

// this class allows the compiler to optimize memory layout for specific record
// type
class RecordObj(val fields: Map[String, Type]):
  def apply(name: String): Option[Type] = fields.get(name)
  // Field offset in bits
  // Note: The offset is in bits instead of bytes. Booleans may be represented
  // using a bit, and nested records may be represented in packed format.
  // Some objects in the IR may not be representable as a pointer, as the offset
  // may not be a multiple of 8, but the analysis pass should guarantee that the
  // final result (passed as function call) should be a valid pointer.
  def getFieldOffset(name: String): Int = ???

  override def toString(): String = "{" + fields
    .map((name, ty) => s"$name: ${ty.toString()}")
    .mkString(", ") + "}"

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

enum Type:
  case Unit
  case Boolean
  case Int32
  case Float32
  case OpaquePointer
  case Record(val impl: RecordObj)
  case Variant(val impl: VariantObj)
  case Function(val args: List[Type], val ret: Type)
  case TypeName(val name: String)

  // TypeName equality is nominal, and we do not perform structural equality
  // because the same type can admit different representations.
  // OpaquePointers always equal to other OpaquePointers, even though their
  // runtime type may be different.
  def eq(other: Type): Boolean =
    (this, other) match
      case (Unit, Unit) | (Boolean, Boolean) | (Int32, Int32) |
          (Float32, Float32) | (OpaquePointer, OpaquePointer) =>
        true
      case (TypeName(n1), TypeName(n2)) => n1 == n2
      case (Function(args1, ret1), Function(args2, ret2)) =>
        args1.length == args2.length && args1
          .zip(args2)
          .forall((a, b) => a.eq(b)) && ret1.eq(ret2)
      // reference equality, same structure different reference are different
      case _ => this eq other

  def size: Int = ???

  override def toString(): String = this match
    case Unit                => "()"
    case Boolean             => "bool"
    case Int32               => "i32"
    case Float32             => "f32"
    case OpaquePointer       => "Any"
    case Record(impl)        => impl.toString()
    case Variant(impl)       => impl.toString()
    case Function(args, ret) => s"(${args.mkString(", ")}) -> $ret"
    case TypeName(name)      => name
