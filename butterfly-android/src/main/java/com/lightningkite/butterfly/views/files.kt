package com.lightningkite.butterfly.views

import android.net.Uri
import com.lightningkite.butterfly.bytes.Data
import com.lightningkite.butterfly.net.HttpClient
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers

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