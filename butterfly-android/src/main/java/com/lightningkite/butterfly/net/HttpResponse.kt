package com.lightningkite.butterfly.net

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import com.lightningkite.butterfly.IsCodable
import com.lightningkite.butterfly.PlatformSpecific
import com.lightningkite.butterfly.bytes.Data
import com.lightningkite.butterfly.fromJsonString
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

val Response.code: Int get() = this.code()
val Response.headers: Map<String, String> get() = this.headers().toMultimap().mapValues { it.value.joinToString(";") }

fun Response.discard(): Single<Unit> {
    return with(HttpClient){
        Single.create<Unit> { em ->
            body()!!.close()
            em.onSuccess(Unit)
        }.threadCorrectly()
    }
}
fun Response.readText(): Single<String> {
    return with(HttpClient){
        Single.create<String> { em -> em.onSuccess(body()!!.use { it.string() }) }.threadCorrectly()
    }
}
fun Response.readData(): Single<Data> {
    return with(HttpClient){
        Single.create<Data> { em -> em.onSuccess(body()!!.use { it.bytes() }) }.threadCorrectly()
    }
}

inline fun <reified T> Response.readJson(): Single<T> = readJson(jacksonTypeRef())

@PlatformSpecific fun <T> Response.readJson(typeToken: TypeReference<T>): Single<T> {
    return with(HttpClient){
        Single.create<T> { em: SingleEmitter<T> ->
            try {
                val result: T = body()!!.use {
                    HttpClient.mapper.readValue<T>(it.byteStream(), typeToken)
                }
                em.onSuccess(result)
            } catch(e: Throwable){
                em.tryOnError(e)
            }
        }.threadCorrectly<T>()
    }
}

inline fun <reified T> Response.readJsonDebug(): Single<T> = readJsonDebug(jacksonTypeRef())

@PlatformSpecific fun <T> Response.readJsonDebug(typeToken: TypeReference<T>): Single<T> {
    return with(HttpClient){
        Single.create<T> { em: SingleEmitter<T> ->
            try {
                val data = body()!!.use { it.string() }
                Log.d("HttpResponse", "Got '$data'")
                val result: T = HttpClient.mapper.readValue<T>(data, typeToken)
                em.onSuccess(result)
            } catch(e: Throwable){
                em.tryOnError(e)
            }
        }.threadCorrectly<T>()
    }
}
