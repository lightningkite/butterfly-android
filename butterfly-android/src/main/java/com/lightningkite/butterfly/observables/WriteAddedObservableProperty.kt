//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import com.lightningkite.butterfly.Escaping
import io.reactivex.Observable

class WriteAddedObservableProperty<A>(
    val basedOn: ObservableProperty<A>,
    val onWrite: @Escaping() (A) -> Unit
) : MutableObservableProperty<A>() {
    override var value: A
        get() = basedOn.value
        set(value) {
            onWrite(value)
        }
    override val onChange: Observable<Box<A>> get() = basedOn.onChange
    override fun update() {
        //Do nothing
    }
}

fun <T> ObservableProperty<T>.withWrite(
    onWrite: @Escaping() (T) -> Unit
): MutableObservableProperty<T> {
    return WriteAddedObservableProperty<T>(this, onWrite)
}
