package com.lightningkite.butterfly

import android.graphics.Bitmap
import android.net.Uri
import com.lightningkite.rxkotlinproperty.forever

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
