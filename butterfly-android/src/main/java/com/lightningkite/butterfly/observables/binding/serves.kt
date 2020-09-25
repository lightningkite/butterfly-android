//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables.binding

import com.lightningkite.butterfly.AnyObject
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.observable
import com.lightningkite.butterfly.rx.DisposeCondition
import com.lightningkite.butterfly.rx.addWeak
import com.lightningkite.butterfly.rx.until
import io.reactivex.rxkotlin.subscribeBy

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

