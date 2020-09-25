package com.lightningkite.butterfly.views


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.MediaStore
import android.util.DisplayMetrics
import androidx.core.content.FileProvider
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.location.GeoCoordinate
import com.lightningkite.butterfly.views.startIntent
import java.io.File
import java.util.*

/**
 * Starts an intent with a direct callback.
 */
fun ActivityAccess.startIntent(
    intent: Intent,
    options: Bundle = android.os.Bundle(),
    onResult: (Int, Intent?) -> Unit = { _, _ -> }
) {
    activity?.startActivityForResult(intent, prepareOnResult(onResult = onResult), options)
}

fun ActivityAccess.share(shareTitle: String, message: String? = null, url: String? = null, image: Image? = null) {
    val i = Intent(Intent.ACTION_SEND)
    i.type = "text/plain"
    listOfNotNull(message, url).joinToString("\n").takeUnless { it == null }?.let { i.putExtra(Intent.EXTRA_TEXT, it) }
    if (image != null) {
        when (image) {
            is ImageReference -> {
                i.setType("image/jpeg")
                i.putExtra(Intent.EXTRA_STREAM, image.uri)
            }
            is ImageRemoteUrl -> {
                i.setType("image/jpeg")
                i.putExtra(Intent.EXTRA_STREAM, Uri.parse(image.url))
            }

        }
    }
    context.startActivity(Intent.createChooser(i, shareTitle))
}

fun ActivityAccess.openUrl(url: String): Boolean {
    val mgr = context.packageManager
    val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
    val list = mgr.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
    return if (list.size > 0) {
        startIntent(intent = intent)
        true
    } else {
        false
    }
}

fun ActivityAccess.openAndroidAppOrStore(packageName: String) {
    val mgr = context.packageManager
    val intent = mgr.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        startIntent(intent = intent)
    } else {
        openUrl("market://details?id=$packageName")
    }
}

fun ActivityAccess.openIosStore(numberId: String) {
    openUrl("https://apps.apple.com/us/app/taxbot/id$numberId")
}

fun ActivityAccess.openMap(coordinate: GeoCoordinate, label: String? = null, zoom: Float? = null) {
    startIntent(
        intent = Intent(Intent.ACTION_VIEW).apply {
            if (label == null) {
                if (zoom == null) {
                    data = Uri.parse("geo:${coordinate.latitude},${coordinate.longitude}")
                } else {
                    data = Uri.parse("geo:${coordinate.latitude},${coordinate.longitude}?z=$zoom")
                }
            } else {
                if (zoom == null) {
                    data = Uri.parse("geo:${coordinate.latitude},${coordinate.longitude}?q=${Uri.encode(label)}")
                } else {
                    data =
                        Uri.parse("geo:${coordinate.latitude},${coordinate.longitude}?q=${Uri.encode(label)}&z=$zoom")
                }
            }
        }
    )
}

fun ActivityAccess.openEvent(title: String, description: String, location: String, start: Date, end: Date) {
    startIntent(
        intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, description)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.time)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.time)
            putExtra(CalendarContract.Events.EVENT_LOCATION, location)
        }
    )
}
