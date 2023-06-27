package mlscript
package compiler.backend

import scala.collection.mutable.{ListBuffer, HashMap}
import scala.collection.mutable.{Set => MutSet, Map => MutMap}
import mlscript.codegen._
import mlscript.utils.shorthands.*
import mlscript.utils.*
import mlscript.codegen.Helpers.*
import mlscript.Term

class Mls2ir {
  val visitedSymbols = MutSet[RuntimeSymbol]()
  val symbolTypeMap = HashMap[Str, Type]()
  val scope = Scope("root")
  val varMap = HashMap[Str, Operand.Var]()
  val entryBB = BasicBlock("entry", Nil, ListBuffer())
  var bb = entryBB

  def termToType(term: Term): Type = term match
    case Var("int")    => Type.Int32
    case Var("unit")   => Type.Unit
    case Var("bool")   => Type.Boolean
    case Var("string") => Type.OpaquePointer
    case _             => ???

  def termToOperand(term: Term): Operand = term match
    case IntLit(v)  => Operand.Const(v.toInt)
    case DecLit(v)  => Operand.Const(v.toFloat)
    case StrLit(v)  => Operand.Const(v)
    case UnitLit(_) => Operand.Unit
    case _          => ???

  def makeCall(name: Str, fn: Operand, args: List[Operand])(implicit
      scope: Scope
  ): Operand =
    val result: Operand.Var = Operand.Var(scope.allocateRuntimeName(name))
    bb.instructions += Instruction.Call(Some(result), fn, args)
    result

  def addTmpVar(name: Str, value: PureValue)(implicit
      scope: Scope
  ): Operand.Var =
    scope.declareValue(name, Some(false), false) // FIXME byvaluerec, islambda
    val result: Operand.Var = Operand.Var(scope.allocateRuntimeName(name))
    varMap += name -> result
    bb.instructions += Instruction.Assignment(result, value)
    result

  def addTmpVar(value: PureValue)(implicit scope: Scope): Operand.Var =
    val result: Operand.Var = Operand.Var(scope.allocateRuntimeName)
    bb.instructions += Instruction.Assignment(result, value)
    result

  def translateVar(name: Str, isCallee: Bool)(implicit scope: Scope): Operand =
    scope.resolveValue(name) match
      case S(sym: BuiltinSymbol) =>
        name match
          case "true"  => Operand.Const(true)
          case "false" => Operand.Const(false)
          case _ => ??? // TODO: How should we deal with the remaining builtins?
      case S(sym: StubValueSymbol) =>
        if sym.accessible then varMap(sym.runtimeName)
        else throw new UnimplementedError(sym)
      case S(sym: ValueSymbol) =>
        if sym.isByvalueRec.getOrElse(false) && !sym.isLam then
          throw CodeGenError(
            s"unguarded recursive use of by-value binding name"
          )
        visitedSymbols += sym
        val op = varMap(sym.runtimeName)
        if sym.isByvalueRec.isEmpty && !sym.isLam then
          makeCall(s"${sym.runtimeName}_result", op, Nil)
        else op
      case S(sym: MixinSymbol)    => varMap(sym.runtimeName)
      case S(sym: ModuleSymbol)   => varMap(sym.runtimeName)
      case S(sym: NewClassSymbol) => varMap(sym.runtimeName)
      case S(sym: NewClassMemberSymbol) =>
        if sym.isByvalueRec.getOrElse(false) && !sym.isLam then
          throw CodeGenError(
            s"unguarded recursive use of by-value binding $name"
          )
        val selfSym = scope.resolveValue("this").get
        visitedSymbols += selfSym
        val self = varMap(selfSym.runtimeName)
        if !symbolTypeMap.contains(selfSym.runtimeName) then
          // FIXME: add cast if needed
          ???
        val result = addTmpVar(
          s"this_${sym.runtimeName}",
          PureValue.GetField(self, sym.runtimeName)
        )
        if sym.isByvalueRec.isEmpty && !sym.isLam then
          makeCall(s"this_${sym.runtimeName}_result", result, Nil)
        else result
      case S(sym: ClassSymbol) =>
        if isCallee then
          addTmpVar(
            s"new_${sym.runtimeName}",
            PureValue.Alloc(symbolTypeMap(sym.runtimeName))
          )
        else ??? // FIXME: lifter should handle lambda?
      case S(sym: TraitSymbol) => ??? // unimplemented for now
      case N => throw CodeGenError(s"unresolved symbol ${name}")

  def translateApp(term: App)(implicit scope: Scope): Operand = term match
    case App(
          App(Var(op), Tup((N -> Fld(_, _, lhs)) :: Nil)),
          Tup((N -> Fld(_, _, rhs)) :: Nil)
        ) if BinOpKind.getBinOp(op).isDefined =>
      val lhsOp = translateTerm(lhs)
      val rhsOp = translateTerm(rhs)
      addTmpVar(PureValue.BinOp(BinOpKind.getBinOp(op).get, lhsOp, rhsOp))
    case App(
          App(
            App(Var("if"), Tup((_, Fld(_, _, tst)) :: Nil)),
            Tup((_, Fld(_, _, con)) :: Nil)
          ),
          Tup((_, Fld(_, _, alt)) :: Nil)
        ) =>
      val tstOp = translateTerm(tst)
      val result: Operand.Var = Operand.Var(scope.allocateRuntimeName)
      val trueBB =
        BasicBlock(scope.allocateRuntimeName("true"), Nil, ListBuffer())
      val falseBB =
        BasicBlock(scope.allocateRuntimeName("false"), Nil, ListBuffer())
      val joinBB = BasicBlock(
        scope.allocateRuntimeName("join"),
        List(result),
        ListBuffer()
      )
      bb.instructions += Instruction.Match(
        tstOp,
        Map(IntLit(1) -> (trueBB, Nil)),
        (falseBB, Nil)
      )
      bb = trueBB
      val conOp = translateTerm(con)
      bb.instructions += Instruction.Branch(joinBB, List(conOp))
      bb = falseBB
      val altOp = translateTerm(alt)
      bb.instructions += Instruction.Branch(joinBB, List(altOp))
      bb = joinBB
      result
    case App(App(App(Var("if"), tst), con), alt) => die
    case App(trm, Tup(args)) =>
      val callee = trm match
        case Var(nme) => translateVar(nme, true)
        case _        => translateTerm(trm)
      val arguments = args map { case (_, Fld(_, _, arg)) =>
        translateTerm(arg)
      }
      makeCall("app_result", callee, arguments)
    case _ => throw CodeGenError(s"ill-formed application ${inspect(term)}")

  def translateCase(term: CaseOf): Operand = ???

  def translateTerm(term: Term)(implicit scope: Scope): Operand = term match
    case _ if term.desugaredTerm.isDefined =>
      translateTerm(term.desugaredTerm.getOrElse(die))
    case Var(name) => translateVar(name, false)
    case Super()   => ??? // FIXME: need to check the semantics
    case Lam(_, _) => ??? // should be handled by lifter
    case t: App    => translateApp(t)
    case Rcd(fields) => // FIXME: what representation should we use?
      val recordtpe = Type.Record(
        RecordObj(
          fields
            .map((name, field) => {
              name.name -> termToType(field.value)
            })
            .to(MutMap)
        )
      )
      addTmpVar(PureValue.Alloc(recordtpe))
    case Sel(receiver, fieldName) =>
      val receiverOp = translateTerm(receiver)
      // FIXME: add cast
      addTmpVar(PureValue.GetField(receiverOp, fieldName.name))
    case Let(true, Var(name), Lam(_, _), _) =>
      ??? // should be handled by lifter
    case Let(true, Var(name), _, _) =>
      throw new CodeGenError(
        s"recursive non-function definition $name is not supported"
      )
    case Let(false, Var(name), value, body) =>
      val letScope = scope.derive("Let")
      val runtimeName = letScope.declareParameter(name)
      val lhs: Operand.Var = Operand.Var(runtimeName)
      varMap += runtimeName -> lhs
      bb.instructions += Instruction.Assignment(
        lhs,
        PureValue.Op(translateTerm(value))
      )
      translateTerm(body)(letScope)
    case Blk(stmts) =>
      val blkScope = scope.derive("Blk")
      val flattened = stmts.iterator.flatMap(_.desugared._2).toList
      flattened.iterator.zipWithIndex.foreach {
        case (t: Term, index) =>
          val result = translateTerm(t)(blkScope)
          if index + 1 == flattened.length then
            bb.instructions += Instruction.Return(Some(result))
          else result
        case (NuFunDef(isLetRec, Var(nme), _, L(rhs)), _) =>
          val result = translateTerm(rhs)(blkScope)
          val pat = blkScope.declareValue(nme, isLetRec, isLetRec.isEmpty)
          val lhs: Operand.Var = Operand.Var(pat.runtimeName)
          varMap += pat.runtimeName -> lhs
          bb.instructions += Instruction.Assignment(
            lhs,
            PureValue.Op(result)
          )
        case (_: Def | _: TypeDef | _: NuFunDef /* | _: NuTypeDef */, _) =>
          throw CodeGenError("unsupported definitions in blocks")
      }
      Operand.Unit
    case c: CaseOf => translateCase(c)
    case IntLit(value) =>
      if value.isValidInt then Operand.Const(value.toInt)
      else throw CodeGenError(s"integer literal $value is too large")
    case DecLit(value)          => Operand.Const(value.toFloat)
    case StrLit(value)          => Operand.Const(value)
    case UnitLit(_)             => Operand.Unit
    case Asc(trm, _)            => translateTerm(trm)
    case With(trm, Rcd(fields)) => ???
    case Bra(_, trm)            => translateTerm(trm)
    // FIXME: array related stuff requires intrinsic functions
    case Tup(terms)       => ???
    case Subs(arr, idx)   => ???
    case Assign(lhs, rhs) => ??? // FIXME: we have assign???
    case Inst(bod)        => translateTerm(bod)
    case iff: If          => translateIf(iff)
    case New(_, _)        => ??? // FIXME: need to check semantics too
    case Forall(_, bod)   => translateTerm(bod)
    case TyApp(base, _)   => translateTerm(base)
    case _: Bind | _: Test | If(_, _) | _: Splc | _: Where =>
      throw CodeGenError(s"cannot generate code for term ${inspect(term)}")

  def translateIf(term: If)(implicit scope: Scope): Operand =

    def translateChildBlock(term: Term, target: BasicBlock, next: BasicBlock) =
      bb = target
      translateTerm(term)
      bb.instructions += Instruction.Branch(next, Nil)

    term match
      case If(body, elze) =>
        body match
          case IfThen(iff, thenn) =>
            val result = Operand.Var(scope.allocateRuntimeName)
            val trueBB =
              BasicBlock(scope.allocateRuntimeName(), Nil, ListBuffer())
            val falseBB =
              BasicBlock(scope.allocateRuntimeName(), Nil, ListBuffer())
            val nextBB =
              BasicBlock(scope.allocateRuntimeName(), Nil, ListBuffer())
            elze match
              case Some(term) =>
                bb.instructions += Instruction.Match(
                  translateTerm(iff),
                  Map(IntLit(1) -> (trueBB, Nil)),
                  (falseBB, Nil)
                )
                translateChildBlock(term, falseBB, nextBB)
              case None =>
                bb.instructions += Instruction.Match(
                  translateTerm(iff),
                  Map(IntLit(1) -> (trueBB, Nil)),
                  (nextBB, Nil)
                )
            translateChildBlock(thenn, trueBB, nextBB)
            bb = nextBB
            result
          case IfOpApp(lhs, op, IfBlock(lines)) =>
            val parent = bb
            val nextBB =
              BasicBlock(scope.allocateRuntimeName(), Nil, ListBuffer())
            var default = (nextBB, Nil)
            var children = MutMap.empty[SimpleTerm, (BasicBlock, List[Operand])]
            lines.foreach(_ match
              case Left(IfThen(App(Var(name), Tup(fields)), thenn)) =>
                val thenBlock = BasicBlock(
                  scope.allocateRuntimeName(),
                  Nil,
                  ListBuffer()
                )
                val args = fields.map(_ match
                  case (_, Fld(_, _, term)) => termToOperand(term)
                  case _ => throw CodeGenError(s"unsupported if statement")
                )
                translateChildBlock(thenn, thenBlock, nextBB)
                children += Var(name) -> (thenBlock, args)
              case Left(IfElse(term)) =>
                val elseBlock = BasicBlock(
                  scope.allocateRuntimeName(),
                  Nil,
                  ListBuffer()
                )
                default = (elseBlock, Nil)
                translateChildBlock(term, elseBlock, nextBB)
              case _ => throw CodeGenError(s"unsupported if statement")
            )
            parent.instructions += Instruction.Match(
              translateTerm(lhs),
              children.toMap,
              default
            )
            bb = nextBB
            val result: Operand.Var = Operand.Var(scope.allocateRuntimeName)
            result
          case others => throw CodeGenError(s"unsupported if statement")

  def translateTypingUnit(unit: TypingUnit)(implicit scope: Scope): Unit =
    unit.entities.foreach { entity =>
      entity match
        case LetS(isRec, pat, rhs)    => ???
        case DataDefn(body)           => ???
        case DatatypeDefn(head, body) => ???
        case NuTypeDef(
              kind,
              nme,
              tparams,
              params,
              ctor, // TODO handle
              sig,
              parents,
              superAnnot,
              thisAnnot,
              body
            ) =>
          kind match
            case Cls =>
              scope.declareClass(
                nme.name,
                params.getOrElse(Tup(Nil)).fields.collect {
                  case S(variable) -> field =>
                    variable.name
                },
                TypeName(nme.name),
                Nil
              )
              symbolTypeMap += nme.name -> Type.TypeName(nme.name)
            case Trt => ???
            case Mxn => ???
            case Als => ???
            case Mod => ???
          translateTypingUnit(body)
        case term: Term =>
          translateTerm(term) // FIXME handle return value
        case NuFunDef(isLetRec, nme, tparams, rhs) =>
          isLetRec match
            case None       => ???
            case Some(true) => ???
            case Some(false) =>
              rhs match
                case Left(term) =>
                  addTmpVar(nme.name, PureValue.Op(translateTerm(term)))
                case Right(tpe) => ???
        case TypeDef(
              kind,
              nme,
              tparams,
              body,
              mthDecls,
              mthDefs,
              positionals
            ) =>
          ???
        case Def(rec, nme, rhs, isByname) => ???
    }

  def apply(unit: TypingUnit): BasicBlock =
    translateTypingUnit(unit)(Scope("root"))
    entryBB
}
