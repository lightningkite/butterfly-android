//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.boxWrap
import io.reactivex.Observable

class TransformedMutableObservableProperty2<A, B>(
    val basedOn: MutableObservableProperty<A>,
    val read: @Escaping() (A) -> B,
    val write: @Escaping() (A, B) -> A
) : MutableObservableProperty<B>() {
    override fun update() {
        basedOn.update()
    }

    override var value: B
        get() {
            return read(basedOn.value)
        }
        set(value) {
            basedOn.value = write(basedOn.value, value)
        }
    override val onChange: Observable<Box<B>> get() {
        val readCopy = read
        return basedOn.onChange.map { it -> boxWrap(readCopy(it.value)) }
    }
}

fun <T, B> MutableObservableProperty<T>.mapWithExisting(
    read: @Escaping() (T) -> B,
    write: @Escaping() (T, B) -> T
): MutableObservableProperty<B> {
    return TransformedMutableObservableProperty2<T, B>(this, read, write)
}
