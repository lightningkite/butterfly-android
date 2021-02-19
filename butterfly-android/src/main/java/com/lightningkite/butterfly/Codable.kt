package com.lightningkite.butterfly

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.lightningkite.butterfly.net.HttpClient

/**
 * Codable is used to indicate that the class in question will be serialized/deserialized using Jackson JSON.
 */


fun IsCodable?.toJsonString(): String {
    return HttpClient.mapper.writeValueAsString(this)
}

inline fun <reified T: IsCodable> String.fromJsonString(): T? {
    return try {
        HttpClient.mapper.readValue(this, jacksonTypeRef<T>())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T: IsCodable> String.fromJsonString(type: TypeReference<T>): T? {
    return try {
        HttpClient.mapper.readValue(this, type)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
fun <T: IsCodable> String.fromJsonString(clazz: Class<T>): T? {
    return try {
        HttpClient.mapper.readValue(this, clazz)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.fromJsonStringUntyped(): IsCodable? {
    return try {
        HttpClient.mapper.readValue(this, Any::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

