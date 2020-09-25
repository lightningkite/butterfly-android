package com.lightningkite.butterfly.observables.binding

import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.ImageView
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.observables.*
import com.lightningkite.butterfly.rx.removed
import com.lightningkite.butterfly.rx.until
import com.lightningkite.butterfly.views.loadImage
import com.lightningkite.butterfly.views.loadVideoThumbnail

/**
 *
 * Binds the imageview internal image to the image provided by the observable.
 * Any changes to the observable will cause a reload of the image to match the change.
 * An image can be from multiple sources, such as the web, an android image reference,
 * and a direct bitmap. It will handle all cases and load the image.
 *
 */
fun ImageView.bindImage(image: ObservableProperty<Image?>) {
    image.subscribeBy { it ->
        post {
            this.loadImage(it)
        }
    }.until(this.removed)
}

fun ImageView.bindVideoThumbnail(video: ObservableProperty<Video?>) {
    video.subscribeBy {
        post {
            this.loadVideoThumbnail(it)
        }
    }.until(removed)
}