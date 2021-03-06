//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.rx.addWeak
import com.lightningkite.butterfly.rx.forever
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

val <T> ObservableProperty<T>.observable: Observable<Box<T>> get() = Observable.concat(Observable.create { it.onNext(boxWrap(value)); it.onComplete() }, onChange)
val <T> ObservableProperty<T>.observableNN: Observable<T> get() = Observable.concat(Observable.create { it.onNext(boxWrap(value)); it.onComplete() }, onChange).map { it -> it.value }
val <T> ObservableProperty<T>.onChangeNN: Observable<T> get() = onChange.map { it -> it.value }

@CheckReturnValue
inline fun <T> ObservableProperty<T>.subscribeBy(
    noinline onError: @Escaping() (Throwable) -> Unit = { it -> it.printStackTrace() },
    noinline onComplete: @Escaping() () -> Unit = {},
    crossinline onNext: @Escaping() (T) -> Unit = { it -> }
): Disposable = this.observable.subscribeBy(
    onError = onError,
    onComplete = onComplete,
    onNext = { boxed -> onNext(boxed.value) }
)

fun <E> includes(collection: MutableObservableProperty<Set<E>>, element: E): MutableObservableProperty<Boolean> {
    return collection.map { it ->
        it.contains(element)
    }.withWrite { it ->
        if (it) {
            collection.value = collection.value.plus(element)
        } else {
            collection.value = collection.value.minus(element)
        }
    }
}

fun ObservableProperty<Boolean>.whileActive(action: @Escaping() () -> Disposable): Disposable {
    var current: Disposable? = null
    return this.subscribeBy {
        if (it) {
            if (current == null) {
                current = action()
            }
        } else {
            current?.dispose()
            current = null
        }
    }
}