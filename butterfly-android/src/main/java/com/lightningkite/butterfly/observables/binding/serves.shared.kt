package com.lightningkite.butterfly.observables.binding

import com.lightningkite.butterfly.AnyObject
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.observable
import com.lightningkite.butterfly.rx.DisposeCondition
import com.lightningkite.butterfly.rx.addWeak
import com.lightningkite.butterfly.rx.until
import io.reactivex.rxkotlin.subscribeBy

@Deprecated("Use something else for binding now")
fun <T> MutableObservableProperty<T>.serves(whilePresent: AnyObject, other: MutableObservableProperty<T>) {

    var suppress = false

    other.observable.addWeak(whilePresent, { ignored, value ->
        if (!suppress) {
            suppress = true
            this.value = value.value
            suppress = false
        }
    })

    this.onChange.addWeak(whilePresent, { ignored, value ->
        if (!suppress) {
            suppress = true
            other.value = value.value
            suppress = false
        }
    })
}

fun <T> MutableObservableProperty<T>.serves(until: DisposeCondition, other: MutableObservableProperty<T>) {

    var suppress = false

    other.observable.subscribeBy { value ->
        if (!suppress) {
            suppress = true
            this.value = value.value
            suppress = false
        }
    }.until(until)

    this.onChange.subscribeBy { value ->
        if (!suppress) {
            suppress = true
            other.value = value.value
            suppress = false
        }
    }.until(until)
}

