package com.lightningkite.butterfly

import android.graphics.PointF
import android.media.MediaMetadataRetriever
import android.os.Build
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.butterfly.views.ViewStringRaw
import com.lightningkite.butterfly.views.showDialog
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Video.thumbnail(timeMs: Long = 2000L, size: PointF? = null): Single<Image> {
    return Single.create<Image> { em ->
        try {
            val mMMR = when (this) {
                is VideoReference -> {
                    val mMMR = MediaMetadataRetriever()
                    mMMR.setDataSource(HttpClient.appContext, this.uri)
                    mMMR
                }
                is VideoRemoteUrl -> {
                    val mMMR = MediaMetadataRetriever()
                    mMMR.setDataSource(this.url, HashMap<String, String>())
                    mMMR
                }
            }
            if (size != null && Build.VERSION.SDK_INT >= 27) {
                val frame = mMMR.getScaledFrameAtTime(
                    timeMs ,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC,
                    size.x.toInt(),
                    size.y.toInt()
                )
                if(frame != null){
                    em.onSuccess(ImageBitmap(frame))
                } else {
                    em.onError(Exception("No frame could be retrieved"))
                }
            } else {
                val frame = mMMR.getFrameAtTime(timeMs)
                if(frame != null){
                    em.onSuccess(ImageBitmap(frame))
                } else {
                    em.onError(Exception("No frame could be retrieved"))
                }
            }
        } catch (e: Exception) {
            em.onError(e)
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}