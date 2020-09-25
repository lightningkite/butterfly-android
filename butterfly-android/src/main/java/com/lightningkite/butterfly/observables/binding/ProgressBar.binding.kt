package com.lightningkite.butterfly.observables.binding

import android.widget.ProgressBar
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.butterfly.observables.subscribeBy
import com.lightningkite.butterfly.rx.removed
import com.lightningkite.butterfly.rx.until

fun ProgressBar.bindInt(
    observable: ObservableProperty<Int>
){
    observable.subscribeBy { value ->
        this.progress = value
    }.until(this@bindInt.removed)
}

fun ProgressBar.bindLong(
    observable: ObservableProperty<Long>
){
    observable.subscribeBy { value ->
        this.progress = value.toInt()
    }.until(this@bindLong.removed)
}

fun ProgressBar.bindFloat(
    observable: ObservableProperty<Float>
){
    observable.subscribeBy { value ->
        this.progress = (value.coerceIn(0.0f, 1.0f) * 100).toInt()
    }.until(this@bindFloat.removed)
}