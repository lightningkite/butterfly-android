@file:Suppress("DEPRECATION")

package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.butterfly.rx.addWeak
import com.lightningkite.rxkotlinproperty.*
import com.lightningkite.rxkotlinproperty.Box
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

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("CombineManyProperty", "com.lightningkite.rxkotlinproperty.CombineManyProperty"))
typealias CombineManyObservableProperty<IN> = CombineManyProperty<IN>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("CombineProperty", "com.lightningkite.rxkotlinproperty.CombineProperty"))
typealias CombineObservableProperty<T, A, B> = CombineProperty<T, A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ConstantProperty", "com.lightningkite.rxkotlinproperty.ConstantProperty"))
typealias ConstantObservableProperty<T> = ConstantProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("RxTransformationOnlyProperty", "com.lightningkite.rxkotlinproperty.RxTransformationOnlyProperty"))
typealias RxTransformationOnlyObservableProperty<T> = RxTransformationOnlyProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("EventToProperty", "com.lightningkite.rxkotlinproperty.EventToProperty"))
typealias EventToObservableProperty<T> = EventToProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("FlatMappedProperty", "com.lightningkite.rxkotlinproperty.FlatMappedProperty"))
typealias FlatMappedObservableProperty<A, B> = FlatMappedProperty<A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("MutableFlatMappedProperty", "com.lightningkite.rxkotlinproperty.MutableFlatMappedProperty"))
typealias MutableFlatMappedObservableProperty<A, B> = MutableFlatMappedProperty<A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("MutableProperty", "com.lightningkite.rxkotlinproperty.MutableProperty"))
typealias MutableObservableProperty<T> = MutableProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("Property", "com.lightningkite.rxkotlinproperty.Property"))
typealias ObservableProperty<T> = Property<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("PropertyStack", "com.lightningkite.rxkotlinproperty.PropertyStack"))
typealias ObservableStack<T> = PropertyStack<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ReferenceProperty", "com.lightningkite.rxkotlinproperty.ReferenceProperty"))
typealias ReferenceObservableProperty<T> = ReferenceProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("SharingProperty", "com.lightningkite.rxkotlinproperty.SharingProperty"))
typealias SharingObservableProperty<T> = SharingProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("StandardProperty", "com.lightningkite.rxkotlinproperty.StandardProperty"))
typealias StandardObservableProperty<T> = StandardProperty<T>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedMutableProperty", "com.lightningkite.rxkotlinproperty.TransformedMutableProperty"))
typealias TransformedMutableObservableProperty<A, B> = TransformedMutableProperty<A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedMutableProperty2", "com.lightningkite.rxkotlinproperty.TransformedMutableProperty2"))
typealias TransformedMutableObservableProperty2<A, B> = TransformedMutableProperty2<A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedProperty", "com.lightningkite.rxkotlinproperty.TransformedProperty"))
typealias TransformedObservableProperty<A, B> = TransformedProperty<A, B>
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("WriteAddedProperty", "com.lightningkite.rxkotlinproperty.WriteAddedProperty"))
typealias WriteAddedObservableProperty<A> = WriteAddedProperty<A>
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("subscribeBy", "com.lightningkite.rxkotlinproperty.subscribeBy"))
inline fun <T> Property<T>.subscribeBy(noinline onError: (Throwable) -> Unit = { it -> it.printStackTrace() }, noinline onComplete: () -> Unit = {}, crossinline onNext: (T) -> Unit = { it -> }): Disposable = new_subscribeBy(onError, onComplete, onNext)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combine", "com.lightningkite.rxkotlinproperty.combine"))
fun <T, B, C> Property<T>.combine(other: Property<B>, combiner: (T, B) -> C): Property<C> = new_combiner(other, combiner)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("map", "com.lightningkite.rxkotlinproperty.map"))
fun <T, B> MutableObservableProperty<T>.map(read: (T) -> B, write: (B) -> T): MutableProperty<B> = this.new_map(read, write)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("map", "com.lightningkite.rxkotlinproperty.map"))
fun <T, B> Property<T>.map(read: (T) -> B): Property<B> = this.new_map(read)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("flatMap", "com.lightningkite.rxkotlinproperty.flatMap"))
fun <T, B> Property<T>.flatMap(transformation: (T) -> Property<B>): FlatMappedProperty<T, B> = this.new_flatMap(transformation)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("mapWithExisting", "com.lightningkite.rxkotlinproperty.mapWithExisting"))
fun <T, B> MutableObservableProperty<T>.mapWithExisting(read: (T) -> B, write: (T, B) -> T): MutableObservableProperty<B> = this.new_mapWithExisting(read, write)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("withWrite", "com.lightningkite.rxkotlinproperty.withWrite"))
fun <T> Property<T>.withWrite(onWrite: (T) -> Unit): MutableProperty<T> = this.new_withWrite(onWrite)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("observable", "com.lightningkite.rxkotlinproperty.observable"))
val <T> Property<T>.observable: Observable<Box<T>> get() = this.new_observable
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("observableNN", "com.lightningkite.rxkotlinproperty.observableNN"))
val <T> Property<T>.observableNN: Observable<T> get() = this.new_observableNN
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("onChangeNN", "com.lightningkite.rxkotlinproperty.onChangeNN"))
val <T> Property<T>.onChangeNN: Observable<T> get() = this.new_onChangeNN
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("share", "com.lightningkite.rxkotlinproperty.share"))
fun <T> Property<T>.share(startAsListening: Boolean = false): SharingProperty<T> = this.new_share(startAsListening)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asObservablePropertyDefaultNull", "com.lightningkite.rxkotlinproperty.asPropertyDefaultNull"))
fun <Element> Observable<Element>.asObservablePropertyDefaultNull(): Property<Element?> = this.new_asObservablePropertyDefaultNull()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("asObservableProperty", "com.lightningkite.rxkotlinproperty.asProperty"))
fun <Element> Observable<Element>.asObservableProperty(defaultValue: Element): Property<Element> = this.new_asObservableProperty(defaultValue)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combined", "com.lightningkite.rxkotlinproperty.combined"))
fun <IN, OUT> List<Property<IN>>.combined(combiner: (List<IN>) -> OUT): Property<OUT> = this.new_combined(combiner)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combined", "com.lightningkite.rxkotlinproperty.combined"))
fun <T> List<Property<T>>.combined(): Property<List<T>> = this.new_combined()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("includes", "com.lightningkite.rxkotlinproperty.includes"))
fun <E> includes(collection: MutableProperty<Set<E>>, element: E): MutableProperty<Boolean> = new_includes(collection, element)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("switchMap", "com.lightningkite.rxkotlinproperty.switchMap"))
fun <T, B> Property<T>.switchMap(transformation: (T) -> Property<B>): FlatMappedProperty<T, B> = this.new_switchMap(transformation)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("switchMapNotNull", "com.lightningkite.rxkotlinproperty.switchMapNotNull"))
fun <T: Any, B: Any> Property<T?>.switchMapNotNull(transformation: (T) -> Property<B?>): FlatMappedProperty<T?, B?> = this.new_switchMapNotNull(transformation)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("flatMapNotNull", "com.lightningkite.rxkotlinproperty.flatMapNotNull"))
fun <T: Any, B: Any> Property<T?>.flatMapNotNull(transformation: (T) -> Property<B?>): FlatMappedProperty<T?, B?> = this.new_flatMapNotNull(transformation)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("switchMapMutable", "com.lightningkite.rxkotlinproperty.switchMapMutable"))
fun <T, B> Property<T>.switchMapMutable(transformation: (T) -> MutableProperty<B>): MutableFlatMappedProperty<T, B> = this.new_switchMapMutable(transformation)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("flatMapMutable", "com.lightningkite.rxkotlinproperty.flatMapMutable"))
fun <T, B> Property<T>.flatMapMutable(transformation: (T) -> MutableProperty<B>): MutableFlatMappedProperty<T, B> = this.new_flatMapMutable(transformation)









