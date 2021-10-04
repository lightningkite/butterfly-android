package com.lightningkite.butterfly

import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.rxkotlinproperty.android.resources.*
import com.lightningkite.rxkotlinproperty.android.resources.ImageBitmap
import com.lightningkite.rxkotlinproperty.android.resources.ImageReference
import com.lightningkite.rxkotlinproperty.android.resources.ImageRemoteUrl
import com.lightningkite.rxkotlinproperty.android.resources.ImageResource
import com.lightningkite.rxkotlinproperty.android.resources.Video
import com.lightningkite.rxkotlinproperty.android.resources.VideoReference
import com.lightningkite.rxkotlinproperty.android.resources.VideoRemoteUrl
import com.lightningkite.rxkotlinproperty.forever
import com.lightningkite.rxkotlinproperty.viewgenerators.animationFrame
import io.reactivex.subjects.PublishSubject

@Deprecated("Please use the correct import.", ReplaceWith("Uri", "android.net.Uri"))
typealias Uri = Uri


@Deprecated("Use Rx Style instead")
fun loadImage(image: Image, onResult: (Bitmap?) -> Unit) {
    image.load().subscribe { result, fail ->
        onResult(result)
    }.forever()
}

@Deprecated("Use Rx Style instead")
fun Image.load(onResult: (Bitmap?) -> Unit) {
    load().subscribe { result, fail ->
        onResult(result)
    }.forever()
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject> captureWeak(capture: Z, lambda: @Escaping() (Z) -> Unit): () -> Unit {
    val captured: Z? by weak(capture)
    return label@{ ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!)
    }
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject, A> captureWeak(capture: Z, lambda: @Escaping() (Z, A) -> Unit): (A) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a)
    }
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject, A, B> captureWeak(capture: Z, lambda: @Escaping() (Z, A, B) -> Unit): (A, B) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b)
    }
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject, A, B, C> captureWeak(capture: Z, lambda: @Escaping() (Z, A, B, C) -> Unit): (A, B, C) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c)
    }
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject, A, B, C, D> captureWeak(
    capture: Z,
    lambda: @Escaping() (Z, A, B, C, D) -> Unit
): (A, B, C, D) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c, d ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c, d)
    }
}

@Deprecated("Weak references are not recommended.")
fun <Z : AnyObject, A, B, C, D, E> captureWeak(
    capture: Z,
    lambda: @Escaping() (Z, A, B, C, D, E) -> Unit
): (A, B, C, D, E) -> Unit {
    val captured: Z? by weak(capture)
    return label@{ a, b, c, d, e ->
        val actualCaptured = captured
        if (actualCaptured == null) {
            return@label
        }
        lambda(actualCaptured!!, a, b, c, d, e)
    }
}

@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("delay", "com.lightningkite.rxkotlinproperty.viewgenerators.delay"))
fun delay(milliseconds: Long, action: () -> Unit) = new_delay(milliseconds, action)
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ApplicationAccess","com.lightningkite.rxkotlinproperty.viewgenerators.ApplicationAccess"))
typealias ApplicationAccess = com.lightningkite.rxkotlinproperty.viewgenerators.ApplicationAccess
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("post", "com.lightningkite.rxkotlinproperty.viewgenerators.post"))
fun post(action: () -> Unit) {Handler(Looper.getMainLooper()).post(action)}
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("animationFrame", "com.lightningkite.rxkotlinproperty.viewgenerators.animationFrame"))
val animationFrame: PublishSubject<Float> get() = new_animationFrame
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("Log", "com.lightningkite.rxkotlinproperty.viewgenerators.Log"))
val Log = new_Log
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("Box", "com.lightningkite.rxkotlinproperty.Box"))
typealias Box<T> = com.lightningkite.rxkotlinproperty.Box<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("Image", "com.lightningkite.rxkotlinproperty.android.resources.Image"))
typealias Image = com.lightningkite.rxkotlinproperty.android.resources.Image
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ImageReference", "com.lightningkite.rxkotlinproperty.android.resources.ImageReference"))
typealias ImageReference = com.lightningkite.rxkotlinproperty.android.resources.ImageReference
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ImageBitmap", "com.lightningkite.rxkotlinproperty.android.resources.ImageBitmap"))
typealias ImageBitmap = com.lightningkite.rxkotlinproperty.android.resources.ImageBitmap
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ImageRaw", "com.lightningkite.rxkotlinproperty.android.resources.ImageRaw"))
typealias ImageRaw = com.lightningkite.rxkotlinproperty.android.resources.ImageRaw
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ImageRemoteUrl", "com.lightningkite.rxkotlinproperty.android.resources.ImageRemoteUrl"))
typealias ImageRemoteUrl = com.lightningkite.rxkotlinproperty.android.resources.ImageRemoteUrl
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ImageResource", "com.lightningkite.rxkotlinproperty.android.resources.ImageResource"))
typealias ImageResource = com.lightningkite.rxkotlinproperty.android.resources.ImageResource
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("Video", "com.lightningkite.rxkotlinproperty.android.resources.Video"))
typealias Video = com.lightningkite.rxkotlinproperty.android.resources.Video
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("VideoReference", "com.lightningkite.rxkotlinproperty.android.resources.VideoReference"))
typealias VideoReference = com.lightningkite.rxkotlinproperty.android.resources.VideoReference
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("VideoRemoteUrl", "com.lightningkite.rxkotlinproperty.android.resources.VideoRemoteUrl"))
typealias VideoRemoteUrl = com.lightningkite.rxkotlinproperty.android.resources.VideoRemoteUrl
@Deprecated("Use the wrap function from Box in RxKotlin Property directly", replaceWith = ReplaceWith("Box.wrap", "com.lightningkite.rxkotlinproperty.Box"))
fun <T> boxWrap(value: T): Box<T> = Box.wrap(value)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("load", "com.lightningkite.rxkotlinproperty.android.resources.load"))
fun Image.load() = this.new_load()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("thumbnail", "com.lightningkite.rxkotlinproperty.android.resources.thumbnail"))
fun Video.thumbnail(timeMs: Long = 2000L, size: PointF? = null) = this.new_thumbnail(timeMs, size)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun String.asImage() = this.new_asImage()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun Uri.asImage() = this.new_asImage()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun Bitmap.asImage() = this.new_asImage()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun DrawableResource.asImage() = this.new_asImage()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asVideo", "com.lightningkite.rxkotlinproperty.android.resources.asVideo"))
fun String.asVideo(): Video = this.new_asVideo()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asVideo", "com.lightningkite.rxkotlinproperty.android.resources.asVideo"))
fun Uri.asVideo(): Video = this.new_asVideo()
