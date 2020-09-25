package com.lightningkite.butterfly.net

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.bytes.Data
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Response
import java.lang.reflect.ParameterizedType

fun Single<@SwiftExactly("Element") Response>.unsuccessfulAsError(): Single<Response> {
    return this.map { it ->
        if (it.isSuccessful) {
            return@map it
        } else {
            throw HttpResponseException(it)
        }
    }
}


fun Single<@SwiftExactly("Element") Response>.discard(): Single<Unit> {
    return this.flatMap {
        if (it.isSuccessful) {
            it.discard()
        } else {
            Single.error<Unit>(HttpResponseException(it)) as Single<Unit>
        }
    }
}

inline fun <reified T> Single<@SwiftExactly("Element") Response>.readJson(): Single<T> {
    val type = jacksonTypeRef<T>()
    return this.flatMap { it ->
        if (it.isSuccessful) {
            it.readJson<T>(type)
        } else {
            Single.error<T>(HttpResponseException(it)) as Single<T>
        }
    }
}

inline fun <reified T> Single<@SwiftExactly("Element") Response>.readJsonDebug(): Single<T> {
    val type = jacksonTypeRef<T>()
    return this.flatMap { it ->
        if (it.isSuccessful) {
            it.readJsonDebug<T>(type)
        } else {
            Single.error<T>(HttpResponseException(it)) as Single<T>
        }
    }
}

fun Single<@SwiftExactly("Element") Response>.readText(): Single<String> {
    return this.flatMap { it ->
        if (it.isSuccessful) {
            it.readText()
        } else {
            Single.error<String>(HttpResponseException(it))
        }
    }
}

fun Single<@SwiftExactly("Element") Response>.readData(): Single<Data> {
    return this.flatMap { it ->
        if (it.isSuccessful) {
            it.readData()
        } else {
            Single.error<Data>(HttpResponseException(it))
        }
    }
}