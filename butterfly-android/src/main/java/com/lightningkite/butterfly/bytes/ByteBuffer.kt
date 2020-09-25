package com.lightningkite.butterfly.bytes

import com.lightningkite.butterfly.PlatformSpecific
import java.nio.*
import java.nio.ByteBuffer

fun Data.buffer(): ByteBuffer {
    return ByteBuffer.wrap(this)
}

fun ByteBuffer.data(): Data = array()

fun ByteBuffer.getUtf8(): String {
    val length = getShort()
    val byteArray = ByteArray(length.toInt())
    this.get(byteArray)
    return byteArray.toString(Charsets.UTF_8)
}

fun ByteBuffer.getUtf8(index: Int): String {
    val beforePosition = position()
    position(index)
    val length = getShort()
    val byteArray = ByteArray(length.toInt())
    this.get(byteArray)
    position(beforePosition)
    return byteArray.toString(Charsets.UTF_8)
}

fun ByteBuffer.putUtf8(string: String): ByteBuffer {
    putShort(string.length.toShort())
    put(string.toByteArray(Charsets.UTF_8))
    return this
}

fun ByteBuffer.putUtf8(index: Int, string: String): ByteBuffer {
    val beforePosition = position()
    position(index)
    putShort(string.length.toShort())
    put(string.toByteArray(Charsets.UTF_8))
    position(beforePosition)
    return this
}

fun ByteBuffer.getSetSizeUtf8(length: Int): String {
    val byteArray = ByteArray(length)
    this.get(byteArray)
    return byteArray.toString(Charsets.UTF_8)
}

fun ByteBuffer.getSetSizeUtf8(length: Int, index: Int): String {
    val beforePosition = position()
    position(index)
    val byteArray = ByteArray(length)
    this.get(byteArray)
    position(beforePosition)
    return byteArray.toString(Charsets.UTF_8)
}

fun ByteBuffer.putSetSizeUtf8(length: Int, string: String): ByteBuffer {
    put(string.take(length).padEnd(length, '\u0000').toByteArray(Charsets.UTF_8))
    return this
}

fun ByteBuffer.putSetSizeUtf8(length: Int, index: Int, string: String): ByteBuffer {
    val beforePosition = position()
    position(index)
    put(string.take(length).padEnd(length, '\u0000').toByteArray(Charsets.UTF_8))
    position(beforePosition)
    return this
}
