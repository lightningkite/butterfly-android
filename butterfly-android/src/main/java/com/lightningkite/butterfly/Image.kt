//! This file will translate using Khrysalis.
package com.lightningkite.butterfly

import android.graphics.Bitmap
import android.net.Uri
import com.lightningkite.butterfly.bytes.Data
import com.lightningkite.butterfly.views.DrawableResource

/**
 *
 * Image is a way to consolidate all the ways an image is described, handled, or created in the app.
 * These are Uri references, a remote URL, a bitmap and a Raw Byte Array.
 *
 */

sealed class Image
data class ImageReference(val uri: Uri): Image()
//In JS, this becomes ImageImageBitmap
data class ImageBitmap(val bitmap: Bitmap): Image()
data class ImageRaw(val raw: Data): Image()
data class ImageRemoteUrl(val url: String): Image()
data class ImageResource(val resource: DrawableResource): Image()

fun String.asImage(): Image = ImageRemoteUrl(this)
fun Uri.asImage(): Image = ImageReference(this)
fun Bitmap.asImage(): Image = ImageBitmap(this)
fun DrawableResource.asImage(): Image = ImageResource(this)
