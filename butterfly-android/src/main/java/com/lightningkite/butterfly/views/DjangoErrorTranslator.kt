//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.net.HttpResponseException
import com.lightningkite.butterfly.net.code
import com.lightningkite.butterfly.net.readText
import com.lightningkite.butterfly.views.StringResource
import io.reactivex.Single
import java.lang.Exception
import java.net.SocketTimeoutException

class DjangoErrorTranslator(
    val connectivityErrorResource: StringResource,
    val serverErrorResource: StringResource,
    val otherErrorResource: StringResource
) {

    fun handleNode(builder: StringBuilder, node: Any?) {
        if(node == null) return
        when (node) {
            is JsonMap -> {
                for ((key, value) in node) {
                    handleNode(builder, value)
                }
            }
            is JsonList -> {
                for(value in node){
                    handleNode(builder, value)
                }
            }
            is String -> {
                //Rough check for human-readability - sentences start with uppercase and will have spaces
                if(node.isNotEmpty() && node[0].isUpperCase() && node.contains(" ")) {
                    builder.appendln(node)
                }
            }
        }
    }
    fun parseError(code: Int, error: String?): ViewString {
        when(code / 100){
            0 -> return ViewStringResource(connectivityErrorResource)
            1, 2, 3 -> {}
            4 -> {
                val errorJson = error?.fromJsonStringUntyped()
                if(errorJson != null){
                    val builder = StringBuilder()
                    handleNode(builder, errorJson)
                    return ViewStringRaw(builder.toString())
                } else {
                    return ViewStringRaw(error ?: "")
                }
            }
            5 -> return ViewStringResource(serverErrorResource)
            else -> {}
        }
        return ViewStringResource(otherErrorResource)
    }

    fun <T> wrap(
        callback: @Escaping() (result: T?, error: ViewString?)->Unit
    ): (code: Int, result: T?, error: String?)->Unit {
        return { code, result, error ->
            callback(result, this.parseError(code, error))
        }
    }

    fun wrapNoResponse(
        callback: @Escaping() (error: ViewString?)->Unit
    ): (code: Int, error: String?)->Unit {
        return { code, error ->
            callback(this.parseError(code, error))
        }
    }

    fun parseException(exception: Any): Single<ViewString> {
        return when(exception){
            is HttpResponseException -> exception.response.readText()
                .map { parseError(exception.response.code, it) }
            is SocketTimeoutException -> Single.just(ViewStringResource(connectivityErrorResource))
            else -> Single.just(ViewStringResource(otherErrorResource))
        }
    }
}
