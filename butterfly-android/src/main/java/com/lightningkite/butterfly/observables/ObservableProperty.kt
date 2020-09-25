//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import io.reactivex.Observable

abstract class ObservableProperty<T> {
    abstract val value: T
    abstract val onChange: Observable<Box<T>>
}

