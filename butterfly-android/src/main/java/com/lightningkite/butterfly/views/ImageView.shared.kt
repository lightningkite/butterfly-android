package com.lightningkite.butterfly.views

import android.media.MediaMetadataRetriever
import android.widget.ImageView
import com.lightningkite.butterfly.Video
import com.lightningkite.butterfly.VideoReference
import com.lightningkite.butterfly.VideoRemoteUrl
import com.lightningkite.butterfly.rx.forever
import com.lightningkite.butterfly.thumbnail
import io.reactivex.rxkotlin.subscribeBy


/**
 *
 * Loads a thumbnail from the video into the imageview the function is called on.
 * Video can be from a local reference or a URL.
 *
 */
fun ImageView.loadVideoThumbnail(video: Video?) {
    if (video == null) return
    loadImage(null)
    video.thumbnail().subscribeBy(
        onError = {
            loadImage(null)
        },
        onSuccess = {
            loadImage(it)
        }
    ).forever()
}