package com.lightningkite.butterfly

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.butterfly.net.unsuccessfulAsError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.max


/**
 *
 * Loads the image and return a Single<Bitmap>. This works for all the types of Images available.
 *
 */
fun Image.load(): Single<Bitmap> {
    return try {
        when (this) {
            is ImageRaw -> Single.just(BitmapFactory.decodeByteArray(this.raw, 0, this.raw.size))
            is ImageReference -> load()
            is ImageBitmap -> Single.just(this.bitmap)
            is ImageRemoteUrl -> load()
            is ImageResource -> {
                val drawable = HttpClient.appContext.resources.getDrawable(resource)
                if (drawable is BitmapDrawable) {
                    Single.just(drawable.bitmap)
                } else {
                    val bitmap = Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    Single.just(bitmap)
                }
            }
        }
    } catch (e: Exception) {
        Single.error(e)
    }
}

/**
 *
 * Loads the image and return a Single<Bitmap> from an ImageReference
 *
 */
private fun ImageReference.load(maxDimension: Int = 2048): Single<Bitmap> {
    return Single.create { emitter ->
        var emitted = false
        Glide.with(HttpClient.appContext)
            .asBitmap()
            .load(this.uri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onError(Exception("Failed to load drawable"))
                    }
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onSuccess(resource)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onError(Exception())
                    }
                }
            })
    }
}

/**
 *
 * Loads the image and return a Single<Bitmap> from an ImageRemoteUrl
 *
 */
private fun ImageRemoteUrl.load(): Single<Bitmap> {
    return Single.create { emitter ->
        var emitted = false
        Glide.with(HttpClient.appContext)
            .asBitmap()
            .load(this.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onError(Exception("Failed to load drawable"))
                    }
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onSuccess(resource)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if(!emitted) {
                        emitted = true
                        emitter.onError(Exception())
                    }
                }
            })
    }
}

//fun Image.shrink(maxDimension: Int = 800): Single<ImageBitmap> {
//    if(this is ImageBitmap && this.bitmap.width <= maxDimension && this.bitmap.height <= maxDimension) {
//        return Single.just(this)
//    }
//    return load()
//        .map {
//            if(it.width <= maxDimension && it.height <= maxDimension){
//                return@map it
//            }
//            val originalAspectRatio = it.width.toFloat() / it.height.toFloat()
//            val newWidth = if(it.width > it.height) maxDimension else (maxDimension * originalAspectRatio).toInt()
//            val newHeight = if(it.width <= it.height) maxDimension else (maxDimension / originalAspectRatio).toInt()
//            return@map Bitmap.createScaledBitmap(it, newWidth, newHeight, true)
//        }
//        .map { ImageBitmap(it) }
//        .subscribeOn(Schedulers.computation())
//        .observeOn(AndroidSchedulers.mainThread())
//}