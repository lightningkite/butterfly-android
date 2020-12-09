package com.lightningkite.butterfly.net

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.graphics.scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.bytes.Data
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import java.io.ByteArrayOutputStream
import java.io.File


fun IsCodable.toJsonHttpBody(): RequestBody {
    val sending = HttpClient.mapper.writeValueAsString(this)
    Log.i("HttpClient", "with body $sending")
    return RequestBody.create(HttpMediaTypes.JSON, sending)
}

fun Data.toHttpBody(mediaType: HttpMediaType): RequestBody {
    return RequestBody.create(mediaType, this)
}

fun String.toHttpBody(mediaType: HttpMediaType = HttpMediaTypes.TEXT): RequestBody {
    return RequestBody.create(mediaType, this)
}

fun Image.toHttpBody(maxDimension: Int = 2048, maxBytes: Long = 10_000_000): Single<RequestBody> = Single.create { em ->
    val glide = Glide.with(HttpClient.appContext).asBitmap()
    val task = when (this) {
        is ImageReference -> glide.load(this.uri)
        is ImageBitmap -> glide.load(this.bitmap)
        is ImageRaw -> glide.load(this.raw)
        is ImageRemoteUrl -> glide.load(this.url)
        is ImageResource -> glide.load(this.resource)
    }
    task
        .addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                e?.printStackTrace() ?: Exception().printStackTrace()
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                em.onSuccess(resource.toHttpBody(maxDimension, maxBytes))
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                em.onError(Exception())
            }
        })
}

fun Bitmap.toHttpBody(maxDimension: Int = 2048, maxBytes: Long = 10_000_000): RequestBody {
    var qualityToTry = 100
    val ratio = this.width.toFloat()/this.height.toFloat()
    val scaledBimap = this.scale(maxDimension, (maxDimension/ratio).toInt())
    var data = ByteArrayOutputStream().use {
        scaledBimap.compress(Bitmap.CompressFormat.JPEG, qualityToTry, it)
        it.toByteArray()
    }
    while (data.size > maxBytes) {
        qualityToTry -= 5
        data = ByteArrayOutputStream().use {
            scaledBimap.compress(Bitmap.CompressFormat.JPEG, qualityToTry, it)
            it.toByteArray()
        }
    }
    return RequestBody.create(HttpMediaTypes.JPEG, data)
}

fun Uri.toHttpBody(): Single<RequestBody> {
    val type = HttpMediaTypes.fromString(HttpClient.appContext.contentResolver.getType(this) ?: "application/octet-stream")
    return Single.just<RequestBody>(object : RequestBody() {
        override fun contentType(): MediaType = type

        override fun writeTo(sink: BufferedSink) {
            HttpClient.appContext.contentResolver.openInputStream(this@toHttpBody)?.use {
                Okio.source(it).use { source ->
                    sink.writeAll(source)
                }
            } ?: throw IllegalStateException("URI (${this@toHttpBody}) could not be opened")
        }
    })
}

fun multipartFormBody(vararg parts: MultipartBody.Part): RequestBody {
    return MultipartBody.Builder().setType(MultipartBody.FORM).also {
        for (part in parts) {
            it.addPart(part)
        }
    }.build()
}

fun multipartFormValuePart(name: String, value: String): MultipartBody.Part = MultipartBody.Part.createFormData(name, value)
fun multipartFormFilePart(name: String, value: String): MultipartBody.Part = MultipartBody.Part.createFormData(name, value)
fun multipartFormFilePart(name: String, filename: String? = null, body: RequestBody): MultipartBody.Part =
    MultipartBody.Part.createFormData(name, filename, body)
