package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.rx.addWeak
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

@Deprecated("Just use RX disposal stuff")
@discardableResult
fun <A : AnyObject, T> ObservableProperty<T>.addAndRunWeak(
    referenceA: A,
    listener: @Escaping() (A, T) -> Unit
): Disposable = observable.addWeak(
    referenceA = referenceA,
    listener = { a, value ->
        listener(
            a,
            value.value
        )
    }
)

@Deprecated("Just use RX disposal stuff")
@discardableResult
fun <A : AnyObject, B : AnyObject, T> ObservableProperty<T>.addAndRunWeak(
    referenceA: A,
    referenceB: B,
    listener: @Escaping() (A, B, T) -> Unit
): Disposable = observable.addWeak(
    referenceA = referenceA,
    referenceB = referenceB,
    listener = { a, b, value ->
        listener(
            a,
            b,
            value.value
        )
    }
)

@Deprecated("Just use RX disposal stuff")
@discardableResult
fun <A : AnyObject, B : AnyObject, C : AnyObject, T> ObservableProperty<T>.addAndRunWeak(
    referenceA: A,
    referenceB: B,
    referenceC: C,
    listener: @Escaping() (A, B, C, T) -> Unit
): Disposable = observable.addWeak(
    referenceA = referenceA,
    referenceB = referenceB,
    referenceC = referenceC,
    listener = { a, b, c, value ->
        listener(
            a,
            b,
            c,
            value.value
        )
    }
)