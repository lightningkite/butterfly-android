package com.lightningkite.butterfly.views


import android.Manifest
import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.location.GeoCoordinate
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.butterfly.views.startIntent
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.*
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
            getIntent.type = "video/*,image/*"
            getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/*,image/*"

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
            getIntent.type = "video/*,image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/*,image/*"

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
    if (uri.scheme.equals("content")) {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).substringAfterLast('/')
            }
        } finally {
            cursor!!.close()
        }
    }
    return null
}

private val DownloadNotificationId: String = "downloads"

/**
 * Using this requires a file provider to be present with the authority '${applicationId}.provider'.
 * See the URL below:
 * https://medium.com/@ali.muzaffar/what-is-android-os-fileuriexposedexception-and-what-you-can-do-about-it-70b9eb17c6d0
 */
fun ActivityAccess.downloadFile(url: String) {
    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
        if (it) {
            val fileBase = url.substringBefore('?').substringAfterLast('/')
            val fileExt = fileBase.substringAfterLast('.')
            val fileWithoutExt = fileBase.substringBeforeLast('.')
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
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
                .setSmallIcon(R.drawable.khrysalis_download_notification)
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
