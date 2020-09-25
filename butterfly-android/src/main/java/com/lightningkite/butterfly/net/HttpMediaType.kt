package com.lightningkite.butterfly.net

import okhttp3.MediaType

object HttpMediaTypes {
    val JSON = MediaType.parse("application/json")!!
    val TEXT = MediaType.parse("text/plain")!!
    val JPEG = MediaType.parse("image/jpeg")!!
    fun fromString(text: String): MediaType = MediaType.parse(text)!!
}
