//! This file is Khrysalis compatible.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import io.reactivex.Observable

class ConstantObservableProperty<T>(val underlyingValue: T) : ObservableProperty<T>() {
    override val onChange: Observable<Box<T>> = Observable.never()
    override val value: T
        get() = underlyingValue
}
