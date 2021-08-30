@file:Suppress("DEPRECATION")

package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.rx.addWeak
import com.lightningkite.rxkotlinproperty.observable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

@Deprecated("Use RX directly instead", ReplaceWith("Observable<Element>", "io.reactivex.Observable"))
typealias Event<Element> = Observable<Element>

@Deprecated("Use RX directly instead", ReplaceWith("Subject<Element>", "io.reactivex.subjects.Subject"))
typealias InvokableEvent<Element> = Subject<Element>

@Deprecated("Use RX directly instead", ReplaceWith("Subject<Element>", "io.reactivex.subjects.PublishSubject"))
typealias StandardEvent<Element> = PublishSubject<Element>


@Deprecated("Just use RX disposal stuff")
@DiscardableResult
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
@DiscardableResult
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
@DiscardableResult
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

@Deprecated("Use RX directly instead", ReplaceWith("onNext(value)"))
fun <Element> Observer<Element>.invokeAll(value: Element) = onNext(value)

@Deprecated("Use 'map' instead", ReplaceWith(
    "this.map(read, write)",
    "com.lightningkite.butterfly.observables.map"
)
)
fun <T, B> MutableObservableProperty<T>.transformed(
    read: @Escaping() (T) -> B,
    write: @Escaping() (B) -> T
): MutableObservableProperty<B> {
    return TransformedMutableObservableProperty<T, B>(this, read, write)
}

@Deprecated("Use 'map' instead", ReplaceWith(
    "this.map(read)",
    "com.lightningkite.butterfly.observables.map"
)
)
fun <T, B> ObservableProperty<T>.transformed(read: @Escaping() (T) -> B): ObservableProperty<B> {
    return TransformedObservableProperty<T, B>(this, read)
}


@Deprecated("Use something else for binding now")
fun <T> MutableObservableProperty<T>.serves(whilePresent: AnyObject, other: MutableObservableProperty<T>) {

    var suppress = false

    other.observable.addWeak(whilePresent, { _, value ->
        if (!suppress) {
            suppress = true
            this.value = value.value
            suppress = false
        }
    })

    this.onChange.addWeak(whilePresent, { _, value ->
        if (!suppress) {
            suppress = true
            other.value = value.value
            suppress = false
        }
    })
}