package com.lightningkite.butterfly.views


import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.CalendarContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.DisplayMetrics
import androidx.core.content.FileProvider
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.location.GeoCoordinate
import com.lightningkite.butterfly.views.startIntent
import java.io.File
import java.util.*

fun ActivityAccess.requestImagesGallery(
    callback: (List<Uri>) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { hasPermission ->
        if (hasPermission) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                if (code == Activity.RESULT_OK) {
                    result?.clipData?.let { clipData ->
                        callback((0 until clipData.itemCount).map { index -> clipData.getItemAt(index).uri })
                    } ?: result?.data?.let { callback(listOf(it)) }
                }
            }
        }
    }
}

fun ActivityAccess.requestImageGallery(
    callback: (Uri) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                val uri = result?.data
                if (code == Activity.RESULT_OK && uri != null) {
                    callback(uri)
                }
            }
        }
    }
}

fun ActivityAccess.requestImageCamera(
    front: Boolean = false,
    callback: (Uri) -> Unit
) {
    val fileProviderAuthority = context.packageName + ".fileprovider"
    val file = File(context.cacheDir, "images").also { it.mkdirs() }
        .let { File.createTempFile("image", ".jpg", it) }
        .let { FileProvider.getUriForFile(context, fileProviderAuthority, it) }
    requestPermission(Manifest.permission.CAMERA) {
        if (it) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
            //TODO:Test this on an older device. This works on newest, but we need to make sure it works/doesn't crash a newer one.
            if (front) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
//                }else{
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1)

//                }
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
            }
            startIntent(intent) { code, result ->
                if (code == Activity.RESULT_OK) callback(result?.data ?: file)
            }
        }
    }
}


fun ActivityAccess.requestVideoGallery(
    callback: (Uri) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "video/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Video")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                val uri = result?.data
                if (code == Activity.RESULT_OK && uri != null) {
                    callback(uri)
                }
            }
        }
    }
}

fun ActivityAccess.requestVideosGallery(
    callback: (List<Uri>) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "video/*"
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Video")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                if (code == Activity.RESULT_OK) {
                    result?.clipData?.let { clipData ->
                        callback((0 until clipData.itemCount).map { index -> clipData.getItemAt(index).uri })
                    } ?: result?.data?.let { callback(listOf(it)) }
                }
            }
        }
    }
}


fun ActivityAccess.requestVideoCamera(
    front: Boolean = false,
    callback: (Uri) -> Unit
) {
    val fileProviderAuthority = context.packageName + ".fileprovider"
    val file = File(context.cacheDir, "videos").also { it.mkdirs() }
        .let { File.createTempFile("video", ".mp4", it) }
        .let { FileProvider.getUriForFile(context, fileProviderAuthority, it) }
    requestPermission(Manifest.permission.CAMERA) {
        if (it) {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
            //TODO:Test this on an older device. This works on newest, but we need to make sure it works/doesn't crash a newer one.
            if (front) {
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1)
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
            }
            startIntent(intent) { code, result ->
                if (code == Activity.RESULT_OK) callback(result?.data ?: file)
            }
        }
    }
}


fun ActivityAccess.requestMediasGallery(
    callback: (List<Uri>) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "video/* image/*"
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/* image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Media")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                if (code == Activity.RESULT_OK) {
                    result?.clipData?.let { clipData ->
                        callback((0 until clipData.itemCount).map { index -> clipData.getItemAt(index).uri })
                    } ?: result?.data?.let { callback(listOf(it)) }
                }
            }
        }
    }
}
fun ActivityAccess.requestMediaGallery(
    callback: (Uri) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "video/* image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/* image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Media")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                val uri = result?.data
                if (code == Activity.RESULT_OK && uri != null) {
                    callback(uri)
                }
            }
        }
    }
}

fun ActivityAccess.requestFile(
    callback: (Uri) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "*/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "*/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select File")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                val uri = result?.data
                if (code == Activity.RESULT_OK && uri != null) {
                    callback(uri)
                }
            }
        }
    }
}

fun ActivityAccess.requestFiles(
    callback: (List<Uri>) -> Unit
) {
    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
        if (it) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            getIntent.type = "*/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "*/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Files")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            this.startIntent(chooserIntent) { code, result ->
                if (code == Activity.RESULT_OK) {
                    result?.clipData?.let { clipData ->
                        callback((0 until clipData.itemCount).map { index -> clipData.getItemAt(index).uri })
                    } ?: result?.data?.let { callback(listOf(it)) }
                }
            }
        }
    }
}

fun ActivityAccess.getMimeType(
    uri: Uri
): String? {
    val cr = context.contentResolver
    return cr.getType(uri)
}

fun ActivityAccess.getFileName(uri: Uri): String? {
    var result: String? = null
    if (uri.scheme.equals("content")) {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor!!.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}

fun ActivityAccess.downloadFile(url: String) {
    val uri: Uri = Uri.parse(url)
    val fileName = url.substringAfterLast('/')

    val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(uri)
    request.setTitle(fileName)
    request.setDescription("Downloading")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

    downloadmanager.enqueue(request)
}
