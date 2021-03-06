package com.lightningkite.butterfly.net

import okhttp3.Response

open class HttpResponseException(val response: Response, cause: Throwable? = null): Exception("Got code ${response.code}", cause)
class HttpReadResponseException(response: Response, val text: String, cause: Throwable? = null): HttpResponseException(response, cause)
