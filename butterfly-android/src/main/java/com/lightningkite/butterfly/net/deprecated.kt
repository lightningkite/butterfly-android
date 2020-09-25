package com.lightningkite.butterfly.net

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response


@Deprecated("Use it directly instead.", ReplaceWith("RequestBody", "okhttp3.RequestBody"))
typealias HttpBody = RequestBody
@Deprecated("Use it directly instead.", ReplaceWith("MultipartBody.Part", "okhttp3.MultipartBody"))
typealias HttpBodyPart = MultipartBody.Part
@Deprecated("Use it directly instead.", ReplaceWith("MediaType", "okhttp3.MediaType"))
typealias HttpMediaType = MediaType
@Deprecated("Use it directly instead.", ReplaceWith("Response", "okhttp3.Response"))
typealias HttpResponse = Response