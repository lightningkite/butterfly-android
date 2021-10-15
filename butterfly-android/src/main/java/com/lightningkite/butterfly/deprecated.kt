package com.lightningkite.butterfly

import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.rxkotlinproperty.android.resources.*
import com.lightningkite.rxkotlinproperty.android.resources.ImageBitmap
import com.lightningkite.rxkotlinproperty.android.resources.ImageReference
import com.lightningkite.rxkotlinproperty.android.resources.ImageRemoteUrl
import com.lightningkite.rxkotlinproperty.android.resources.ImageResource
import com.lightningkite.rxkotlinproperty.android.resources.Video
import com.lightningkite.rxkotlinproperty.android.resources.VideoReference
import com.lightningkite.rxkotlinproperty.android.resources.VideoRemoteUrl
import com.lightningkite.rxkotlinproperty.viewgenerators.LogInterface
import com.lightningkite.rxkotlinproperty.viewgenerators.animationFrame
import io.reactivex.rxjava3.subjects.PublishSubject

@Deprecated("Please use the correct import.", ReplaceWith("Uri", "android.net.Uri"))
typealias Uri = Uri


@Deprecated("Use Rx Style instead")
fun loadImage(image: Image, onResult: (Bitmap?) -> Unit): Unit = no()

@Deprecated("Use Rx Style instead")
fun Image.load(onResult: (Bitmap?) -> Unit): Unit = no()

private fun no(): Nothing = throw NotImplementedError()

@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("delay", "com.lightningkite.rxkotlinproperty.viewgenerators.delay"))
fun delay(milliseconds: Long, action: () -> Unit): Unit = no()
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ApplicationAccess","com.lightningkite.rxkotlinproperty.viewgenerators.ApplicationAccess"))
typealias ApplicationAccess = com.lightningkite.rxkotlinproperty.viewgenerators.ApplicationAccess
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("post", "com.lightningkite.rxkotlinproperty.viewgenerators.post"))
fun post(action: () -> Unit) {Handler(Looper.getMainLooper()).post(action)}
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("animationFrame", "com.lightningkite.rxkotlinproperty.viewgenerators.animationFrame"))
val animationFrame: PublishSubject<Float>
    get() = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("Log", "com.lightningkite.rxkotlinproperty.viewgenerators.Log"))
val Log: LogInterface get() = no()
@Deprecated("No longer exists in any way")
typealias Box<T> = List<T>
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
fun <T> boxWrap(value: T): Box<T> = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("load", "com.lightningkite.rxkotlinproperty.android.resources.load"))
fun Image.load(): Bitmap = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("thumbnail", "com.lightningkite.rxkotlinproperty.android.resources.thumbnail"))
fun Video.thumbnail(timeMs: Long = 2000L, size: PointF? = null): Image = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun String.asImage(): Image = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun Uri.asImage(): Image = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun Bitmap.asImage(): Image = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asImage", "com.lightningkite.rxkotlinproperty.android.resources.asImage"))
fun DrawableResource.asImage(): Image = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asVideo", "com.lightningkite.rxkotlinproperty.android.resources.asVideo"))
fun String.asVideo(): Video = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asVideo", "com.lightningkite.rxkotlinproperty.android.resources.asVideo"))
fun Uri.asVideo(): Video = no()

/**
 *
 * Modifies the string to make it a more readable. This is capitalize the first letter, remove . and underscores through the string.
 *
 */

@Deprecated("Use your own implementation if you really need it")
fun String.humanify(): String {
    if(this.isEmpty()) return ""
    return this[0].toUpperCase() + this.replace(".", " - ").replace(Regex("[A-Z]")){ result ->
        " " + result.value
    }.replace('_', ' ').trim()
}

/**
 *  Returns a new string with all occurrences of [char] removed.
 */
@Deprecated("Use your own implementation if you really need it")
fun String.remove(char:Char):String{
    return splitToSequence(char).joinToString(separator = "")
}
/**
 *  Returns a new string with all occurrences of [sequence] removed.
 */
@Deprecated("Use your own implementation if you really need it")
fun String.remove(sequence:String):String{
    return splitToSequence(sequence).joinToString(separator = "")
}

/**
 *
 * Modifies the string to make follow the snake case standard. Adds underscore before an uppercase char, and then makes them all lowercase.
 *
 */
@Deprecated("Use your own implementation if you really need it")
fun String.toSnakeCase(): String {
    val builder = StringBuilder(this.length * 3 / 2)
    for (char in this) {
        if (char.isUpperCase()) {
            builder.append('_')
            builder.append(char.toLowerCase())
        } else {
            builder.appendln()
            builder.append(char)
        }
    }
    return builder.toString().trim('_')
}

@Deprecated("Use your own implementation if you really need it")
fun String.formatList(arguments: List<Any?>) = this.format(*arguments.toTypedArray())

@Deprecated("Use your own implementation if you really need it")
fun <T> List<T>.withoutIndex(index: Int): List<T> {
    return this.toMutableList().apply { removeAt(index) }
}

@Deprecated("Use your own implementation if you really need it")
fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum: Long = 0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

@Deprecated("Use your own implementation if you really need it")
inline fun <T, K : Comparable<K>> MutableList<T>.binaryInsertBy(
    item: T,
    crossinline selector: (T) -> K?
) {
    val index = binarySearchBy(selector(item), selector = selector)
    if (index < 0) {
        add(
            index = -index - 1,
            element = item
        )
    } else {
        add(
            index = index,
            element = item
        )
    }
}

@Deprecated("Use your own implementation if you really need it")
inline fun <T, K : Comparable<K>> MutableList<T>.binaryInsertByDistinct(
    item: T,
    crossinline selector: (T) -> K?
): Boolean {
    val index = binarySearchBy(selector(item), selector = selector)
    if (index < 0) {
        add(
            index = -index - 1,
            element = item
        )
        return true
    } else {
        return false
    }
}


@Deprecated("Use your own implementation if you really need it")
inline fun <T, K : Comparable<K>> List<T>.binaryFind(
    key: K,
    crossinline selector: (T) -> K?
): T? {
    val index = binarySearchBy(key, selector = selector)
    if(index >= 0){
        return this[index]
    } else {
        return null
    }
}


@Deprecated("Use your own implementation if you really need it")
inline fun <T, K : Comparable<K>> List<T>.binaryForEach(
    lower: K,
    upper: K,
    crossinline selector: (T) -> K?,
    action: (T)->Unit
) {
    var index = binarySearchBy(lower, selector = selector)
    if(index < 0){
        index = -index - 1
    }
    while(index < size){
        val item = this[index]
        val itemK = selector(item)
        if(itemK == null || itemK > upper) break
        action(item)
        index++
    }
}

@Deprecated("Use your own implementation if you really need it")
fun Int.floorMod(other: Int): Int = (this % other + other) % other
@Deprecated("Use your own implementation if you really need it")
fun Int.floorDiv(other: Int): Int {
    if(this < 0){
        return this / other - 1
    } else {
        return this / other
    }
}

@Deprecated("Use your own implementation if you really need it")
fun Float.floorMod(other: Float): Float = (this % other + other) % other
@Deprecated("Use your own implementation if you really need it")
fun Float.floorDiv(other: Float): Float {
    if(this < 0){
        return this / other - 1
    } else {
        return this / other
    }
}


@Deprecated("Use your own implementation if you really need it")
fun Double.floorMod(other: Double): Double = (this % other + other) % other
@Deprecated("Use your own implementation if you really need it")
fun Double.floorDiv(other: Double): Double {
    if(this < 0){
        return this / other - 1
    } else {
        return this / other
    }
}
