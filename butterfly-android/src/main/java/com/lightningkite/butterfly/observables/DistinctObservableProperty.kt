//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.SwiftExtensionWhere
import io.reactivex.Observable

class RxTransformationOnlyObservableProperty<T>(
    val basedOn: ObservableProperty<T>,
    val operator: @Escaping() (Observable<Box<T>>) -> Observable<Box<T>>
) : ObservableProperty<T>() {
    override val value: T
        get() = basedOn.value

    override val onChange: Observable<Box<T>> get() = operator(basedOn.onChange)
}

@SwiftExtensionWhere("T: Equatable")
fun <T> ObservableProperty<T>.distinctUntilChanged(): ObservableProperty<T> = this.plusRx { it.startWith(Box.wrap(value)).distinctUntilChanged().skip(1) }

fun <T> ObservableProperty<T>.plusRx(operator: @Escaping() (Observable<Box<T>>) -> Observable<Box<T>>): ObservableProperty<T> {
    return RxTransformationOnlyObservableProperty<T>(this, operator)
}