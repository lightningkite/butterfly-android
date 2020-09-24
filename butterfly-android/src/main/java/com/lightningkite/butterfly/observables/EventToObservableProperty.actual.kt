package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.Box
import io.reactivex.Observable


fun <T> Observable<Box<T>>.asObservablePropertyUnboxed(defaultValue: T): ObservableProperty<T> {
    return EventToObservableProperty<T>(defaultValue, this)
}
