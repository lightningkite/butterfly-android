package com.lightningkite.butterfly.observables.binding

import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.butterfly.observables.observable
import com.lightningkite.butterfly.observables.observableNN
import com.lightningkite.butterfly.observables.subscribeBy
import com.lightningkite.butterfly.rx.removed
import com.lightningkite.butterfly.rx.until
import com.lightningkite.butterfly.views.widget.VideoPlayer
import com.lightningkite.butterfly.views.onClick

fun VideoPlayer.bind(video: ObservableProperty<Video?>){
    bindVideoToView(this, video)
}

fun PlayerView.bind(video: ObservableProperty<Video?>) {
    bindVideoToView(this, video)
}

private fun bindVideoToView(view:PlayerView, video: ObservableProperty<Video?>){
    val player: SimpleExoPlayer = SimpleExoPlayer.Builder(view.context).build()
    view.player = player
    video.observable.doOnDispose { player.release() }.subscribe { videoBox ->
        val video = videoBox.value
        if(video == null) {
            player.stop()
            player.clearVideoSurface()
            return@subscribe
        }
        when (video) {
            is VideoReference -> {
                val agent = Util.getUserAgent(view.context, view.context.getString(R.string.app_name))
                val factory = DefaultDataSourceFactory(view.context, agent)
                val source = ProgressiveMediaSource.Factory(factory).createMediaSource(video.uri)
                player.prepare(source)
            }
            is VideoRemoteUrl -> {
                val agent = Util.getUserAgent(view.context, view.context.getString(R.string.app_name))
                val factory = DefaultDataSourceFactory(view.context, agent)
                val source = ProgressiveMediaSource.Factory(factory).createMediaSource(android.net.Uri.parse(video.url))
                player.prepare(source)
            }
            else -> {
            }
        }
    }.until(view.removed)
}

fun VideoPlayer.bindAndStart(video: ObservableProperty<Video?>){
    bindVideoToViewAndStart(this, video)
}

fun PlayerView.bindAndStart(video: ObservableProperty<Video?>) {
    bindVideoToViewAndStart(this, video)
}

private fun bindVideoToViewAndStart(view:PlayerView, video:ObservableProperty<Video?>){
    val player: SimpleExoPlayer = SimpleExoPlayer.Builder(view.context).build()
    view.player = player
    video.observable.doOnDispose { player.release() }.subscribe { videoBox ->
        val video = videoBox.value
        if(video == null) {
            player.stop()
            player.clearVideoSurface()
            return@subscribe
        }
        when (video) {
            is VideoReference -> {
                val agent = Util.getUserAgent(view.context, view.context.getString(R.string.app_name))
                val factory = DefaultDataSourceFactory(view.context, agent)
                val source = ProgressiveMediaSource.Factory(factory).createMediaSource(video.uri)
                player.playWhenReady = true
                player.prepare(source)
            }
            is VideoRemoteUrl -> {
                val agent = Util.getUserAgent(view.context, view.context.getString(R.string.app_name))
                val factory = DefaultDataSourceFactory(view.context, agent)
                val source = ProgressiveMediaSource.Factory(factory).createMediaSource(Uri.parse(video.url))
                player.playWhenReady = true
                player.prepare(source)
            }
            else -> {
            }
        }
    }.until(view.removed)
}