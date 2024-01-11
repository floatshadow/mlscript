package mlscript.compiler.wasm

import mlscript.utils.shorthands.*


object Symbol:
  @inline def nameMangling(clsName: Str, methodName: Str) =
    s"_Z${clsName}_${methodName}"
  @inline def vtableMangling(clsName: Str) =
    s"_ZVT_${clsName}"
  @inline def itableMangling(clsName: Str, traitName: Str) =
    s"_ZIT_${clsName}_${traitName}"
  @inline def traitImplSym(clsName: Str, traitName: Str) =
    s"${clsName}@${traitName}"
  @inline def builtinItableSearch =
    s"__mlscript_invokeinterface"
  @inline def typeNameMangling(clsName: Str, methodName: Str) =
    s"_ZTYP_${clsName}_${methodName}"
  @inline def typeIfaceNameMangling(clsName: Str, traitName: Str, methodName: Str) =
    s"_ZTYP_${clsName}@${traitName}_${methodName}"

