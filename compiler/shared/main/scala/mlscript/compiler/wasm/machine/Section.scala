package mlscript.compiler.wasm

import mlscript.compiler.*
import mlscript.utils.*
import mlscript.utils.shorthands.*
import mlscript.*

import scala.collection.mutable.ArrayBuffer


// encoding data in wasm data segments
class DataString(
    val data: Array[Byte]
):
  override def toString(): String =
    data map { byte => s"\\${byte}%02x"} mkString ""

  def concate(rhs: DataString): DataString =
    DataString(this.data.concat(rhs.data))

object DataString:

    final val byteWidth = 8

    // little-endian encoding
    @inline def parseInt(value: Int) =
        f"${value}%08x".sliding(2, 2).map(Integer.parseInt(_, 16).toByte).toArray.reverse

    def fromInt(value: Int) =
        DataString(parseInt(value))
    
    def fromIntSeq(seq: IterableOnce[Int]) =
        DataString(seq.iterator.flatMap(parseInt).toArray)