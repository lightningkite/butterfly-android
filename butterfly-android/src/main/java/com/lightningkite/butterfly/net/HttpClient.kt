package com.lightningkite.butterfly.net

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.butterfly.observables.asObservableProperty
import com.lightningkite.butterfly.time.DateAlone
import com.lightningkite.butterfly.time.TimeAlone
import com.lightningkite.butterfly.time.iso8601
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.*
import okio.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object HttpClient {

    private var _appContext: Context? = null

    @PlatformSpecific
    var appContext: Context
        get() = _appContext!!
        set(value) {
            _appContext = value
            ioScheduler = Schedulers.io()
            responseScheduler = AndroidSchedulers.mainThread()
        }

    var ioScheduler: Scheduler? = null
    var responseScheduler: Scheduler? = null
    fun <T> Single<T>.threadCorrectly(): Single<T> {
        var current = this
        if (ioScheduler != null) {
            current = current.subscribeOn(ioScheduler)
        }
        if (responseScheduler != null) {
            current = current.observeOn(responseScheduler)
        }
        return current
    }

    fun <T> Observable<T>.threadCorrectly(): Observable<T> {
        var current = this
        if (ioScheduler != null) {
            current = current.subscribeOn(ioScheduler)
        }
        if (responseScheduler != null) {
            current = current.observeOn(responseScheduler)
        }
        return current
    }

    const val GET = "GET"
    const val POST = "POST"
    const val PUT = "PUT"
    const val PATCH = "PATCH"
    const val DELETE = "DELETE"

    @PlatformSpecific
    val client = OkHttpClient.Builder().build()

    @PlatformSpecific
    val mapper = ObjectMapper()
        .registerModule(KotlinModule())
        .registerModule(SimpleModule(
            "EnumFix",
            Version.unknownVersion(),
            mapOf(
                TimeAlone::class.java to object : StdDeserializer<TimeAlone>(
                    TimeAlone::class.java
                ) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): TimeAlone? {
                        if (p.currentToken == JsonToken.VALUE_NULL) return null
                        return TimeAlone.iso(p.text)
                    }
                },
                DateAlone::class.java to object : StdDeserializer<DateAlone>(
                    DateAlone::class.java
                ) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): DateAlone? {
                        if (p.currentToken == JsonToken.VALUE_NULL) return null
                        return DateAlone.iso(p.text)
                    }
                }
            ),
            listOf(
                object : StdSerializer<TimeAlone>(TimeAlone::class.java) {
                    override fun serialize(value: TimeAlone?, gen: JsonGenerator, provider: SerializerProvider?) {
                        if (value == null) {
                            gen.writeNull()
                        } else {
                            gen.writeString(value.iso8601())
                        }
                    }
                },
                object : StdSerializer<DateAlone>(DateAlone::class.java) {
                    override fun serialize(value: DateAlone?, gen: JsonGenerator, provider: SerializerProvider?) {
                        if (value == null) {
                            gen.writeNull()
                        } else {
                            gen.writeString(value.iso8601())
                        }
                    }
                }
            )
        ))
        .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        .setDateFormat(StdDateFormat().withLenient(true))

    val defaultOptions = HttpOptions()
    fun getCacheControl(cacheMode: HttpCacheMode): CacheControl = when (cacheMode) {
        HttpCacheMode.Default -> CacheControl.Builder().build()
        HttpCacheMode.NoStore -> CacheControl.Builder().noCache().noStore().build()
        HttpCacheMode.Reload -> CacheControl.Builder().noCache().build()
        HttpCacheMode.NoCache -> CacheControl.Builder().maxAge(1000, TimeUnit.DAYS).build()
        HttpCacheMode.ForceCache -> CacheControl.Builder().maxAge(1000, TimeUnit.DAYS).build()
        HttpCacheMode.OnlyIfCached -> CacheControl.Builder().onlyIfCached().build()
    }

    private fun callInternal(
        url: String,
        method: String = HttpClient.GET,
        headers: Map<String, String> = mapOf(),
        body: RequestBody? = null,
        options: HttpOptions = HttpClient.defaultOptions,
        client: OkHttpClient
    ): Call {
        Log.d("HttpClient", "Sending $method request to $url with headers $headers")
        val request = Request.Builder()
            .url(url)
            .method(method, body)
            .headers(Headers.of(headers))
            .addHeader("Accept-Language", Locale.getDefault().language)
            .cacheControl(getCacheControl(options.cacheMode))
            .build()
        return client.newCall(request)
    }

    private fun OkHttpClient.Builder.apply(options: HttpOptions): OkHttpClient.Builder {
        options.callTimeout?.let {
            callTimeout(it, TimeUnit.MILLISECONDS)
        } ?: run {
            callTimeout(5, TimeUnit.MINUTES)
        }
        options.writeTimeout?.let {
            writeTimeout(it, TimeUnit.MILLISECONDS)
        } ?: run {
            writeTimeout(5, TimeUnit.MINUTES)
        }
        options.readTimeout?.let {
            readTimeout(it, TimeUnit.MILLISECONDS)
        } ?: run {
            readTimeout(5, TimeUnit.MINUTES)
        }
        options.connectTimeout?.let {
            connectTimeout(it, TimeUnit.MILLISECONDS)
        } ?: run {
            connectTimeout(5, TimeUnit.MINUTES)
        }
        return this
    }

    fun call(
        url: String,
        method: String = HttpClient.GET,
        headers: Map<String, String> = mapOf(),
        body: RequestBody? = null,
        options: HttpOptions = HttpClient.defaultOptions
    ): Single<Response> {
        return Single.create<Response> { emitter ->
            try {
                val response = callInternal(
                    url = url,
                    method = method,
                    headers = headers,
                    body = body,
                    options = options,
                    client = client.newBuilder().apply(options).build()
                ).execute()
                Log.d("HttpClient", "Response from $method request to $url with headers $headers: ${response.code()}")
                emitter.onSuccess(response)
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }.cache().threadCorrectly()
    }

    fun <T> callWithProgress(
        url: String,
        method: String = HttpClient.GET,
        headers: Map<String, String> = mapOf(),
        body: RequestBody? = null,
        options: HttpOptions = HttpClient.defaultOptions,
        parse: @Escaping() (Response) -> Single<T>
    ): Observable<HttpProgress<T>> {
        var finished = false
        return Observable.create<HttpProgress<T>> { em ->
            val call = callInternal(
                url = url,
                method = method,
                headers = headers,
                body = body?.let {
                    ProgressRequestBody<T>(it) {
                        if (!finished) {
                            em.onNext(it)
                        }
                    }
                },
                options = options,
                client = client.newBuilder().apply(options).addNetworkInterceptor {
                    val original = it.proceed(it.request())
                    original.newBuilder()
                        .body(original.body()?.let {
                            ProgressResponseBody<T>(it) {
                                if (!finished) {
                                    em.onNext(it)
                                }
                            }
                        })
                        .build()
                }.build()
            )
            try {
                val response = call.execute()
                Log.d("HttpClient", "Response from $method request to $url with headers $headers: ${response.code()}")
                parse(response).subscribe(
                    { parsed ->
                        finished = true
                        em.onNext(HttpProgress(HttpPhase.Done, response = parsed))
                        em.onComplete()
                    },
                    { e ->
                        em.tryOnError(e)
                    }
                )
            } catch (e: Exception) {
                em.tryOnError(e)
            }
        }.share().threadCorrectly()
    }

    private class ProgressRequestBody<T>(val base: RequestBody, val onProgress: (HttpProgress<T>) -> Unit) :
        RequestBody() {
        override fun contentType(): MediaType? = base.contentType()
        override fun writeTo(sink: BufferedSink) {
            val bufferedSink: BufferedSink
            val knownSize = base.contentLength()
            val countingSink = if (knownSize == -1L) CountingSink(sink) {
                onProgress(
                    HttpProgress(
                        HttpPhase.Write,
                        estimateProgress(it)
                    )
                )
            } else CountingSink(sink) {
                onProgress(
                    HttpProgress(
                        HttpPhase.Write,
                        (it.toDouble() / knownSize.toDouble()).toFloat()
                    )
                )
            }
            bufferedSink = Okio.buffer(countingSink)
            base.writeTo(bufferedSink)
            bufferedSink.flush()
            onProgress(HttpProgress(phase = HttpPhase.Waiting))
        }

        override fun contentLength(): Long = base.contentLength()
    }

    private fun estimateProgress(bytes: Long): Float = 1f - (1.0 / (bytes.toDouble() / 100_000.0 + 1.0)).toFloat()

    private class CountingSink(delegate: Sink, val action: (Long) -> Unit) : ForwardingSink(delegate) {
        var totalWritten = 0L
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            totalWritten += byteCount
            action(totalWritten)
        }
    }

    private class ProgressResponseBody<T>(val body: ResponseBody, val onProgress: (HttpProgress<T>) -> Unit) :
        ResponseBody() {
        override fun contentType(): MediaType? = body.contentType()
        override fun contentLength(): Long = body.contentLength()
        var src: BufferedSource? = null
        override fun source(): BufferedSource {
            src?.let { return it }
            val knownLength = contentLength()
            val src = if (knownLength == -1L) {
                object : ForwardingSource(body.source()) {
                    var totalBytesRead = 0L

                    @Throws(IOException::class)
                    override fun read(sink: Buffer, byteCount: Long): Long {
                        val bytesRead = super.read(sink, byteCount)
                        // read() returns the number of bytes read, or -1 if this source is exhausted.
                        totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                        onProgress(HttpProgress(HttpPhase.Read, estimateProgress(totalBytesRead)))
                        return bytesRead
                    }

                    override fun close() {
                        super.close()
                    }
                }
            } else {
                object : ForwardingSource(body.source()) {
                    var totalBytesRead = 0L

                    @Throws(IOException::class)
                    override fun read(sink: Buffer, byteCount: Long): Long {
                        val bytesRead = super.read(sink, byteCount)
                        // read() returns the number of bytes read, or -1 if this source is exhausted.
                        totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                        onProgress(
                            HttpProgress(
                                HttpPhase.Read,
                                (totalBytesRead.toDouble() / knownLength.toDouble()).toFloat()
                            )
                        )
                        return bytesRead
                    }

                    override fun close() {
                        super.close()
                    }
                }
            }
            val b = Okio.buffer(src)
            this.src = b
            return b
        }

    }

    @Deprecated("Just use plain call with options")
    fun call(
        url: String,
        method: String = HttpClient.GET,
        headers: Map<String, String> = mapOf(),
        body: RequestBody? = null,
        callTimeout: Long? = null,
        writeTimeout: Long? = null,
        readTimeout: Long? = null,
        connectTimeout: Long? = null
    ): Single<Response> {
        return call(
            url = url,
            method = method,
            headers = headers,
            body = body,
            options = HttpOptions(
                callTimeout = callTimeout,
                writeTimeout = writeTimeout,
                readTimeout = readTimeout,
                connectTimeout = connectTimeout
            )
        )
    }

    fun webSocket(
        url: String
    ): Observable<ConnectedWebSocket> {
        return Observable.using<ConnectedWebSocket, ConnectedWebSocket>(
            {
                val out = ConnectedWebSocket(url)
                out.underlyingSocket = client.newWebSocket(
                    Request.Builder()
                        .url(url.replace("http", "ws"))
                        .addHeader("Accept-Language", Locale.getDefault().language)
                        .build(),
                    out
                )
                out
            },
            { it.ownConnection },
            { it.onComplete() }
        ).threadCorrectly()
    }

}

