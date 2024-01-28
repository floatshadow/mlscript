package mlscript.compiler.wasm

import mlscript.compiler.*
import mlscript.utils.shorthands.*


object Symbol:
  @inline def nameMangling(clsName: Str, methodName: Str) =
    s"_Z${clsName}_${methodName}"
  @inline def ctorMangling(cls: ClassInfo) =
    nameMangling(cls.ident, "constructor")
  @inline def vtableMangling(clsName: Str) =
    s"_ZVT_${clsName}"
  @inline def itableMangling(clsName: Str) =
    s"_ZIT_${clsName}"
  @inline def traitImplSym(clsName: Str, traitName: Str) =
    s"${clsName}@${traitName}"
  @inline def builtinItableSearch =
    s"__mlscript_invokeinterface"
  @inline def typeNameMangling(clsName: Str, methodName: Str) =
    s"_ZTYP_${clsName}_${methodName}"
  @inline def typeIfaceNameMangling(clsName: Str, traitName: Str, methodName: Str) =
    s"_ZTYP_${clsName}@${traitName}_${methodName}"

  // allocator
  @inline def builtinMalloc = "malloc"
  @inline def builtinReuse = "reuse_cell"
  @inline def builtinFree = "free"
  @inline def builtinIncRef = "incref"
  @inline def builtinDecRef = "decref"

  // pretty printers
  @inline def generalShow = "show"
  @inline def objectShow(clsName: Str) = nameMangling(clsName, "show")

  // reserved info
  @inline def builtinUndefined = "undefined"

