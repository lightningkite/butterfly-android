//! This file is Khrysalis compatible.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.boxWrap
import io.reactivex.Observable

class TransformedObservableProperty<A, B>(
    val basedOn: ObservableProperty<A>,
    val read: @Escaping() (A) -> B
) : ObservableProperty<B>() {
    override val value: B
        get() {
            return read(basedOn.value)
        }
    override val onChange: Observable<Box<B>> get() {
        val readCopy = read
        return basedOn.onChange.map { it -> boxWrap(readCopy(it.value)) }
    }
}

fun <T, B> ObservableProperty<T>.map(read: @Escaping() (T) -> B): ObservableProperty<B> {
    return TransformedObservableProperty<T, B>(this, read)
}
