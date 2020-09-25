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

fun ActivityAccess.getString(resource: StringResource): String = context.getString(resource)
fun ActivityAccess.getColor(resource: ColorResource): Int = context.resources.getColor(resource)
val ActivityAccess.displayMetrics: DisplayMetrics get() = context.resources.displayMetrics


