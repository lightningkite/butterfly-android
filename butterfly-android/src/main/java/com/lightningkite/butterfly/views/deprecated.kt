package com.lightningkite.butterfly.views

import android.Manifest
import android.R
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.JsonList
import com.lightningkite.butterfly.JsonMap
import com.lightningkite.butterfly.bytes.Data
import com.lightningkite.butterfly.fromJsonStringUntyped
import com.lightningkite.butterfly.location.GeoCoordinate
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.butterfly.net.HttpResponseException
import com.lightningkite.butterfly.net.code
import com.lightningkite.butterfly.net.readText
import com.lightningkite.rxkotlinproperty.android.resources.Image
import com.lightningkite.rxkotlinproperty.android.resources.Video
import com.lightningkite.rxkotlinproperty.android.resources.ViewStringRaw
import com.lightningkite.rxkotlinproperty.android.resources.ViewStringResource
import com.lightningkite.rxkotlinproperty.android.resources.setFromVideoThumbnail
import com.lightningkite.rxkotlinproperty.android.resources.ViewString
import com.lightningkite.rxkotlinproperty.android.resources.setImage
import com.lightningkite.rxkotlinproperty.android.StringResource
import com.lightningkite.rxkotlinproperty.viewgenerators.*
import com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.util.*

private fun no(): Nothing = throw NotImplementedError()

@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ActivityAccess", "com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess"))
typealias ViewDependency = ActivityAccess

@Deprecated("")
fun ActivityAccess.downloadDrawable(
    url: String,
    width: Int? = null,
    height: Int? = null,
    onResult: (Drawable?) -> Unit
) {
    Picasso.get()
            .load(url)
            .let {
                if (width == null || height == null) it
                else it.resize(width.coerceAtLeast(100), height.coerceAtLeast(100)).centerCrop()
            }
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    onResult(null)
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    onResult(BitmapDrawable(bitmap))
                }
            })
}

@Deprecated("")
fun ActivityAccess.checkedDrawable(
    checked: Drawable,
    normal: Drawable
) = StateListDrawable().apply {
    addState(intArrayOf(R.attr.state_checked), checked)
    addState(intArrayOf(), normal)
}

@Deprecated("")
fun ActivityAccess.setSizeDrawable(drawable: Drawable, width: Int, height: Int): Drawable {
    val scale = context.resources.displayMetrics.density
    return object : LayerDrawable(arrayOf(drawable)) {
        override fun getIntrinsicWidth(): Int = (width * scale).toInt()
        override fun getIntrinsicHeight(): Int = (height * scale).toInt()
    }
}

@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ViewGenerator", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator"))
typealias ViewGenerator = com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator
@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ViewGenerator.Default", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator"))
typealias DefaultViewGenerator = ViewGenerator.Default
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewString", "com.lightningkite.rxkotlinproperty.android.resources.ViewString"))
typealias ViewString = com.lightningkite.rxkotlinproperty.android.resources.ViewString
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringRaw", "com.lightningkite.rxkotlinproperty.android.resources.ViewStringRaw"))
typealias ViewStringRaw = com.lightningkite.rxkotlinproperty.android.resources.ViewStringRaw
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringResource", "com.lightningkite.rxkotlinproperty.android.resources.ViewStringResource"))
typealias ViewStringResource = com.lightningkite.rxkotlinproperty.android.resources.ViewStringResource
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringTemplate", "com.lightningkite.rxkotlinproperty.android.resources.ViewStringTemplate"))
typealias ViewStringTemplate = com.lightningkite.rxkotlinproperty.android.resources.ViewStringTemplate
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringComplex", "com.lightningkite.rxkotlinproperty.android.resources.ViewStringComplex"))
typealias ViewStringComplex = com.lightningkite.rxkotlinproperty.android.resources.ViewStringComplex
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringList", "com.lightningkite.rxkotlinproperty.android.resources.ViewStringList"))
typealias ViewStringList = com.lightningkite.rxkotlinproperty.android.resources.ViewStringList
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("DialogRequest", "com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest"))
typealias DialogRequest = com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("EntryPoint", "com.lightningkite.rxkotlinproperty.viewgenerators.EntryPoint"))
typealias EntryPoint = com.lightningkite.rxkotlinproperty.viewgenerators.EntryPoint
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("StringResource", "com.lightningkite.rxkotlinproperty.android.StringResource"))
typealias StringResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("ColorResource", "com.lightningkite.rxkotlinproperty.android.ColorResource"))
typealias ColorResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("DrawableResource", "com.lightningkite.rxkotlinproperty.android.DrawableResource"))
typealias DrawableResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("HasBackAction", "com.lightningkite.rxkotlinproperty.viewgenerators.HasBackAction"))
typealias HasBackAction = com.lightningkite.rxkotlinproperty.viewgenerators.HasBackAction
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("showDialog", "com.lightningkite.rxkotlinproperty.viewgenerators.showDialog"))
fun showDialog(request: DialogRequest): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("showDialog", "com.lightningkite.rxkotlinproperty.viewgenerators.showDialog"))
fun showDialog(message: ViewString): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("spinnerTextColor", "com.lightningkite.rxkotlinproperty.android.spinnerTextColor"))
var Spinner.spinnerTextColor: Int
    get() = no()
    set(value) { no() }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("spinnerTextSize", "com.lightningkite.rxkotlinproperty.android.spinnerTextSize"))
var Spinner.spinnerTextSize: Double
    get() = no()
    set(value) { no() }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("joinToViewString", "com.lightningkite.rxkotlinproperty.viewgenerators.joinToViewString"))
fun List<ViewString>.joinToViewString(separator: String = "\n"): ViewString = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("setImage", "com.lightningkite.rxkotlinproperty.android.resources.setImage"))
fun ImageView.loadImage(image: Image?): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("setFromVideoThumbnail", "com.lightningkite.rxkotlinproperty.android.resources.setFromVideoThumbnail"))
fun ImageView.loadVideoThumbnail(video: Video?): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("this.onClick", "com.lightningkite.rxkotlinproperty.android.onClick"))
fun View.onClick(disabledMilliseconds: Long = 500, action: () -> Unit): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("onLongClick", "com.lightningkite.rxkotlinproperty.android.onLongClick"))
fun View.onLongClick(action: () -> Unit): Unit = no()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("replace", "com.lightningkite.rxkotlinproperty.android.replace"))
fun View.replace(other: View): Unit = no()

@Deprecated("Use the built in setText instead.")
var TextView.textResource: Int
    get() = 0
    set(value) {
        setText(value)
    }
@Deprecated("Use the built in text or setText instead.")
var TextView.textString: String
    get() = text.toString()
    set(value) {
        this.text = value
    }
@Deprecated("Use the built in setText instead.")
var ToggleButton.textResource: Int
    get() = 0
    set(value) {
        setText(value)
        textOn = resources.getString(value)
        textOff = resources.getString(value)
    }
@Deprecated("User the built in setTextColor instead")
fun TextView.setTextColorResource(color: ColorResource) {
    setTextColor(color)
}
@Deprecated("Use both textOn and textOff separately")
var ToggleButton.textString: String
    get() = text.toString()
    set(value) {
        setText(value)
        textOn = value
        textOff = value
    }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("focusAtStartup", "com.lightningkite.rxkotlinproperty.viewgenerators.focusAtStartup"))
var View.focusAtStartup: Boolean
    get() = no()
    set(value) {
        no()
    }
@Deprecated("Use built in setBackgroundColor")
fun View.setBackgroundColorResource(color: ColorResource) {
    setBackgroundResource(color)
}
@Deprecated("Use the appropriate direct functions for this. That means setBackgroundColor or setBackgroundResource")
var View.backgroundResource: Int
    get() = 0
    set(value) {
        this.setBackgroundResource(value)
    }
@Deprecated("Use built in 'background' instead")
var View.backgroundDrawable: Drawable?
    get() = this.background
    set(value) {
        this.background = value
    }
@Deprecated("Do not use")
fun newEmptyView(dependency: ActivityAccess): View = View(dependency.context)


@Deprecated("Use your own implementation if you really need it")
var Button.compoundDrawable: Drawable?
    get() = compoundDrawables.asSequence().filterNotNull().firstOrNull()
    set(value){
        val existing = compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(
            if(existing[0] != null) value else null,
            if(existing[1] != null) value else null,
            if(existing[2] != null) value else null,
            if(existing[3] != null) value else null
        )
    }

@Deprecated("Use your own implementation if you really need it")
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
    fun parseError(code: Int, error: String?): com.lightningkite.rxkotlinproperty.android.resources.ViewString {
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
        callback: @Escaping() (result: T?, error: com.lightningkite.rxkotlinproperty.android.resources.ViewString?)->Unit
    ): (code: Int, result: T?, error: String?)->Unit {
        return { code, result, error ->
            callback(result, this.parseError(code, error))
        }
    }

    fun wrapNoResponse(
        callback: @Escaping() (error: com.lightningkite.rxkotlinproperty.android.resources.ViewString?)->Unit
    ): (code: Int, error: String?)->Unit {
        return { code, error ->
            callback(this.parseError(code, error))
        }
    }

    fun parseException(exception: Any): Single<com.lightningkite.rxkotlinproperty.android.resources.ViewString> {
        return when(exception){
            is HttpResponseException -> exception.response.readText()
                .map { parseError(exception.response.code, it) }
            is SocketTimeoutException -> Single.just(ViewStringResource(connectivityErrorResource))
            else -> Single.just(ViewStringResource(otherErrorResource))
        }
    }
}

@Deprecated("Use the Android setOnDoneClick directly", ReplaceWith("this.setOnDoneClick { _, _, _ -> action(); true }"), DeprecationLevel.ERROR)
fun EditText.setOnDoneClick(action: () -> Unit): Unit = throw NotImplementedError()

@Deprecated("Use your own implementation if necessary.")
fun Uri.readData(): Single<Data> {
    return Single.create { em: SingleEmitter<Data> ->
        try {
            em.onSuccess(HttpClient.appContext.contentResolver.openInputStream(this)!!.use { it.readBytes() })
        } catch(e:Exception) {
            em.onError(e)
        }
    }.subscribeOn(HttpClient.ioScheduler)
        .observeOn(HttpClient.responseScheduler)
}
/**
 *
 *  When set to true will reverse the direction of the recycler view.
 *  Rather than top to bottom, it will scroll bottom to top.
 *
 */
@Deprecated("Use your own implementation if you really need it")
var RecyclerView.reverseDirection: Boolean
    get() = (this.layoutManager as? LinearLayoutManager)?.reverseLayout ?: false
    set(value) {
        (this.layoutManager as? LinearLayoutManager)?.reverseLayout = value
    }

/**
 *
 *  Provides the RecyclerView a lambda to call when the lambda reaches the end of the list.
 *
 */

@Deprecated("Use your own implementation if you really need it")
fun RecyclerView.whenScrolledToEnd(action: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            (layoutManager as? LinearLayoutManager)?.let {
                if (it.findLastVisibleItemPosition() == adapter?.itemCount?.minus(1)) {
                    action()
                }
            }
        }
    })
}

@Deprecated("Use your own implementation if you really need it")
fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY+ height)
    smoothScrollBy(0, delta)
}

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("startIntent", "com.lightningkite.rxkotlinproperty.viewgenerators.startIntent"), DeprecationLevel.ERROR)
fun ActivityAccess.startIntent(
    intent: Intent,
    options: Bundle = android.os.Bundle(),
    onResult: (Int, Intent?) -> Unit = { _, _ -> }
): Unit = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("share", "com.lightningkite.rxkotlinproperty.viewgenerators.share"), DeprecationLevel.ERROR)
fun ActivityAccess.share(shareTitle: String, message: String? = null, url: String? = null, image: Image? = null): Unit = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("openUrl", "com.lightningkite.rxkotlinproperty.viewgenerators.openUrl"), DeprecationLevel.ERROR)
fun ActivityAccess.openUrl(url: String, newWindow: Boolean = true): Boolean = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("openAndroidAppOrStore", "com.lightningkite.rxkotlinproperty.viewgenerators.openAndroidAppOrStore"), DeprecationLevel.ERROR)
fun ActivityAccess.openAndroidAppOrStore(packageName: String) : Unit = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("openIosStore", "com.lightningkite.rxkotlinproperty.viewgenerators.openIosStore"), DeprecationLevel.ERROR)
fun ActivityAccess.openIosStore(numberId: String) : Unit = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("openMap", "com.lightningkite.rxkotlinproperty.viewgenerators.openMap"), DeprecationLevel.ERROR)
fun ActivityAccess.openMap(coordinate: GeoCoordinate, label: String? = null, zoom: Float? = null) : Unit = throw NotImplementedError()

@Deprecated("Use RxKotlinProperty instead", ReplaceWith("openEvent", "com.lightningkite.rxkotlinproperty.viewgenerators.openEvent"), DeprecationLevel.ERROR)
fun ActivityAccess.openEvent(title: String, description: String, location: String, start: Date, end: Date) : Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestImagesGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestImagesGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestImagesGallery(
    callback: (List<Uri>) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestImageGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestImageGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestImageGallery(
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestImageCamera", "com.lightningkite.rxkotlinproperty.viewgenerators.requestImageCamera"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestImageCamera(
    front: Boolean = false,
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()


@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestVideoGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestVideoGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestVideoGallery(
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestVideosGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestVideosGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestVideosGallery(
    callback: (List<Uri>) -> Unit
): Unit = throw NotImplementedError()


@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestVideoCamera", "com.lightningkite.rxkotlinproperty.viewgenerators.requestVideoCamera"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestVideoCamera(
    front: Boolean = false,
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()


@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestMediasGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestMediasGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestMediasGallery(
    callback: (List<Uri>) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestMediaGallery", "com.lightningkite.rxkotlinproperty.viewgenerators.requestMediaGallery"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestMediaGallery(
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestFile", "com.lightningkite.rxkotlinproperty.viewgenerators.requestFile"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestFile(
    callback: (Uri) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("requestFiles", "com.lightningkite.rxkotlinproperty.viewgenerators.requestFiles"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.requestFiles(
    callback: (List<Uri>) -> Unit
): Unit = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("getMimeType", "com.lightningkite.rxkotlinproperty.viewgenerators.getMimeType"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.getMimeType(
    uri: Uri
): String? = throw NotImplementedError()

@Deprecated(
    "Use RxKotlinProperty instead",
    ReplaceWith("getFileName", "com.lightningkite.rxkotlinproperty.viewgenerators.getFileName"),
    DeprecationLevel.ERROR
)
fun ActivityAccess.getFileName(uri: Uri): String? = throw NotImplementedError()

private val DownloadNotificationId: String = "downloads"

/**
 * Using this requires a file provider to be present with the authority '${applicationId}.provider'.
 * See the URL below:
 * https://medium.com/@ali.muzaffar/what-is-android-os-fileuriexposedexception-and-what-you-can-do-about-it-70b9eb17c6d0
 */
@Deprecated("Replace with your own implementation if needed")
fun ActivityAccess.downloadFile(url: String) {
    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
        if (it) {
            val fileBase = url.substringBefore('?').substringAfterLast('/')
            val fileExt = fileBase.substringAfterLast('.')
            val fileWithoutExt = fileBase.substringBeforeLast('.')
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && try {
                    notificationManager.getNotificationChannel(
                        DownloadNotificationId
                    ) == null
                } catch (e: Throwable) {
                    true
                }
            ) {
                // Create the NotificationChannel
                val mChannel =
                    NotificationChannel(DownloadNotificationId, "Downloads", NotificationManager.IMPORTANCE_LOW)
                mChannel.description = "Downloads"
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(mChannel)
            }

            fun builder() = NotificationCompat.Builder(HttpClient.appContext, DownloadNotificationId)
                .setSmallIcon(com.lightningkite.butterfly.R.drawable.khrysalis_download_notification)
                .setContentTitle("Starting download of $fileBase...")
                .setPriority(NotificationCompat.PRIORITY_LOW)

            val downloadId = url.hashCode()
            NotificationManagerCompat.from(context).notify(
                downloadId,
                builder()
                    .setProgress(100, 0, true)
                    .build()
            )

            Log.i("ActivityAccess.media", "Downloading '$url'...")
            Request.Builder()
                .url(url)
                .get()
                .build()
                .let { HttpClient.client.newCall(it) }
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        NotificationManagerCompat.from(context).notify(
                            downloadId,
                            builder()
                                .setContentTitle("Downloading $fileBase failed.")
                                .build()
                        )
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Schedulers.io().scheduleDirect {
                            try {
                                if (response.isSuccessful) {
                                    Log.i("ActivityAccess.media", "Started copying data for $fileBase.")
                                    response.body()!!.let { body ->

                                        val size = body.contentLength()
                                        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            HttpClient.appContext.contentResolver.insert(
                                                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                                                ContentValues().apply {
                                                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileBase)
                                                    put(
                                                        MediaStore.MediaColumns.MIME_TYPE,
                                                        body.contentType()?.toString()
                                                    )
                                                    put(
                                                        MediaStore.MediaColumns.RELATIVE_PATH,
                                                        Environment.DIRECTORY_DOWNLOADS + "/DIR-S"
                                                    )
                                                })!!
                                        } else {
                                            HttpClient.appContext.contentResolver.insert(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                ContentValues().apply {
                                                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileBase)
                                                    put(
                                                        MediaStore.MediaColumns.MIME_TYPE,
                                                        body.contentType()?.toString()
                                                    )
                                                })!!
                                        }
                                        body.byteStream().use { input ->
                                            HttpClient.appContext.contentResolver.openOutputStream(uri)!!
                                                .use { output ->
                                                    if (size > 0) {
                                                        NotificationManagerCompat.from(context).notify(
                                                            downloadId,
                                                            builder().setContentTitle("Downloading $fileBase...")
                                                                .setProgress(100, 0, false)
                                                                .build()
                                                        )
                                                    } else {
                                                        NotificationManagerCompat.from(context).notify(
                                                            downloadId,
                                                            builder().setContentTitle("Downloading $fileBase...")
                                                                .setProgress(100, 50, true)
                                                                .build()
                                                        )
                                                    }
                                                    var bytesCopied: Long = 0
                                                    var updateCounter: Int = 0
                                                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                                                    var bytes = input.read(buffer)
                                                    while (bytes >= 0) {
                                                        output.write(buffer, 0, bytes)
                                                        bytesCopied += bytes
                                                        updateCounter += bytes
                                                        bytes = input.read(buffer)
                                                        if (updateCounter > 10_000 && size > 0) {
                                                            updateCounter = 0
                                                            Log.i(
                                                                "ActivityAccess.media",
                                                                "Progress for download: $bytesCopied / $size"
                                                            )
                                                            post {
                                                                NotificationManagerCompat.from(context).notify(
                                                                    downloadId,
                                                                    builder()
                                                                        .setContentTitle("Downloading $fileBase...")
                                                                        .setProgress(
                                                                            100,
                                                                            (bytesCopied.toDouble() / size.toDouble() * 100).toInt(),
                                                                            false
                                                                        )
                                                                        .build()
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                        }
                                        post {
                                            try {
                                                NotificationManagerCompat.from(context).notify(
                                                    downloadId,
                                                    builder()
                                                        .setContentTitle("Downloaded $fileBase.")
                                                        .setContentIntent(
                                                            PendingIntent.getActivity(
                                                                HttpClient.appContext,
                                                                0,
                                                                Intent(Intent.ACTION_VIEW).apply {
                                                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                                    data = uri
                                                                },
                                                                PendingIntent.FLAG_CANCEL_CURRENT
                                                            )
                                                        )
                                                        .build()
                                                )
                                                Log.i("ActivityAccess.media", "Download finished for $uri.")
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "ActivityAccess.media",
                                                    "Download had an exception $e when launching final notification."
                                                )
                                                e.printStackTrace()
                                            }
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                Log.e("ActivityAccess.media", "Download had an exception $fileBase.")
                                NotificationManagerCompat.from(context).notify(
                                    downloadId,
                                    builder()
                                        .setContentTitle("Downloading $fileBase failed.")
                                        .build()
                                )
                                e.printStackTrace()
                            }
                        }
                    }
                })
        }
    }
}

/**
 * Using this requires a file provider to be present with the authority '${applicationId}.provider'.
 * See the URL below:
 * https://medium.com/@ali.muzaffar/what-is-android-os-fileuriexposedexception-and-what-you-can-do-about-it-70b9eb17c6d0
 */
fun ActivityAccess.downloadFileData(data: Data, name: String, type: MediaType) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && try {
            notificationManager.getNotificationChannel(
                DownloadNotificationId
            ) == null
        } catch (e: Throwable) {
            true
        }
    ) {
        // Create the NotificationChannel
        val mChannel =
            NotificationChannel(DownloadNotificationId, "Downloads", NotificationManager.IMPORTANCE_LOW)
        mChannel.description = "Downloads"
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(mChannel)
    }
    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
        if (it) {
            try {
                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    HttpClient.appContext.contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                            put(
                                MediaStore.MediaColumns.MIME_TYPE,
                                type.toString()
                            )
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_DOWNLOADS + "/DIR-S"
                            )
                        })!!
                } else {
                    HttpClient.appContext.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                            put(
                                MediaStore.MediaColumns.MIME_TYPE,
                                type.toString()
                            )
                        })!!
                }
                HttpClient.appContext.contentResolver.openOutputStream(uri)!!.use { output ->
                    output.write(data)
                }

                fun builder() = NotificationCompat.Builder(HttpClient.appContext, DownloadNotificationId)
                    .setSmallIcon(com.lightningkite.butterfly.R.drawable.khrysalis_download_notification)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                NotificationManagerCompat.from(context).notify(
                    name.hashCode(),
                    builder()
                        .setContentTitle("Downloaded $name.")
                        .setContentIntent(
                            PendingIntent.getActivity(
                                HttpClient.appContext,
                                0,
                                Intent(Intent.ACTION_VIEW).apply {
                                    this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    this.data = uri
                                },
                                PendingIntent.FLAG_CANCEL_CURRENT
                            )
                        )
                        .build()
                )
            } catch (e: Exception) {
                Log.e("ActivityAccess.media", "Download had an exception $e.")
                e.printStackTrace()
            }
        }
    }
}

@Deprecated("Use RxKotlin Property instead", ReplaceWith("getString", "com.lightningkite.rxkotlinproperty.viewgenerators.getString"), DeprecationLevel.ERROR)
fun ActivityAccess.getString(resource: StringResource): String = throw NotImplementedError()
@Deprecated("Use RxKotlin Property instead", ReplaceWith("getColor", "com.lightningkite.rxkotlinproperty.viewgenerators.getColor"), DeprecationLevel.ERROR)
fun ActivityAccess.getColor(resource: ColorResource): Int = throw NotImplementedError()
@Deprecated("Use RxKotlin Property instead", ReplaceWith("displayMetrics", "com.lightningkite.rxkotlinproperty.viewgenerators.displayMetrics"), DeprecationLevel.ERROR)
val ActivityAccess.displayMetrics: DisplayMetrics
    get() = throw NotImplementedError()



@Deprecated("Use your own implementation if you really need it")
val Context.activity: Activity?
    get() {
        return when(this){
            is Activity -> this
            is ContextWrapper -> this.baseContext.activity
            else -> null
        }
    }
