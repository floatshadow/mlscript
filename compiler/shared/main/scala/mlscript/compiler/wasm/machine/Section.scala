package mlscript.compiler.wasm

import mlscript.compiler.*
import mlscript.utils.*
import mlscript.utils.shorthands.*
import mlscript.*

import scala.collection.mutable.{ArrayBuffer, Map => MutMap}

// encoding data in wasm data segments
class DataString(
  val data: Array[Byte]
):
  override def toString(): String =
    data map { byte => f"\\${byte}%02x"} mkString ""

  def concate(rhs: DataString): DataString =
    DataString(this.data.concat(rhs.data))

  def size =
    data.size

object DataString:

  final val byteWidth = 8

  // little-endian encoding
  @inline private def parseInt(value: Int) =
      f"${value}%08x".sliding(2, 2).map(Integer.parseInt(_, 16).toByte).toArray.reverse

  def fromInt(value: Int) =
    DataString(parseInt(value))
    
  def fromIntSeq(seq: IterableOnce[Int]) =
    DataString(seq.iterator.flatMap(parseInt).toArray)
  
  def zeros(size: Int, width: Int = 32) =
    DataString(Array.fill(size * (width / byteWidth))(0))

class DataSegment(
  segment: MutMap[String, DataString]
):
  import DataSegment.*

  def computeOffsets: List[(String, (Int, DataString))] =
    var size = 0
    segment.toList.map {
      case (name, data) =>
        val offset = size
        size = size + roundUp(data.size)
        name -> (offset, data)
    }

  def memorySize: Int =
    segment.values.foldLeft(0) {
      case (size, data) => size + roundUp(data.size)
    }
  
  def register(name: String, data: DataString) =
    if segment.contains(name) then
      throw WasmBackendError(s"try to register duplicated data symbol ${name}")
    else
      segment.update(name, data)
  
  def registerAll(xs: IterableOnce[(String, DataString)]) =
    xs.iterator.foreach(register)


object DataSegment:
  // alignment should consistent with word size and multiples of 4B
  final val alignment = 4

  @inline def roundUp(size: Int) =
    (size + (alignment - 1)) & ~(alignment - 1)

  def empty =
    DataSegment(MutMap())