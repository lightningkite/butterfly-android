package com.lightningkite.butterfly.bytes

import java.nio.charset.Charset

typealias Data = ByteArray

fun String.toData(charset: Charset): Data = toByteArray(charset)
