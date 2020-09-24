package com.lightningkite.butterfly

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.butterfly.observables.StandardObservableProperty
import com.lightningkite.butterfly.observables.asObservableProperty
import com.lightningkite.butterfly.observables.map
import com.lightningkite.butterfly.views.geometry.GFloat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

fun delay(milliseconds: Long, action: () -> Unit) {
    if (milliseconds == 0L) action()
    else Handler(Looper.getMainLooper()).postDelayed(action, milliseconds)
}

fun post(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post(action)
}

val animationFrame: PublishSubject<GFloat> = PublishSubject.create()

@Deprecated("Moved", ReplaceWith("ApplicationAccess.foreground", "com.lightningkite.butterfly.ApplicationAccess"))
val applicationIsActive: ObservableProperty<Boolean> get() = ApplicationAccess.foreground