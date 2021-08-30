package com.lightningkite.butterfly.observables

import com.lightningkite.rxkotlinproperty.*

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("CombineManyProperty"))
typealias CombineManyObservableProperty<IN> = CombineManyProperty<IN>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("CombineProperty"))
typealias CombineObservableProperty<T, A, B> = CombineProperty<T, A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ConstantProperty"))
typealias ConstantObservableProperty<T> = ConstantProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("RxTransformationOnlyProperty"))
typealias RxTransformationOnlyObservableProperty<T> = RxTransformationOnlyProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("EventToProperty"))
typealias EventToObservableProperty<T> = EventToProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("FlatMappedProperty"))
typealias FlatMappedObservableProperty<A, B> = FlatMappedProperty<A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("MutableFlatMappedProperty"))
typealias MutableFlatMappedObservableProperty<A, B> = MutableFlatMappedProperty<A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("MutableProperty"))
typealias MutableObservableProperty<T> = MutableProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("Property"))
typealias ObservableProperty<T> = Property<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("PropertyStack"))
typealias ObservableStack<T> = PropertyStack<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ReferenceProperty"))
typealias ReferenceObservableProperty<T> = ReferenceProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("SharingProperty"))
typealias SharingObservableProperty<T> = SharingProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("StandardProperty"))
typealias StandardObservableProperty<T> = StandardProperty<T>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedMutableProperty"))
typealias TransformedMutableObservableProperty<A, B> = TransformedMutableProperty<A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedMutableProperty2"))
typealias TransformedMutableObservableProperty2<A, B> = TransformedMutableProperty2<A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("TransformedProperty"))
typealias TransformedObservableProperty<A, B> = TransformedProperty<A, B>

@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("WriteAddedProperty"))
typealias WriteAddedObservableProperty<A> = WriteAddedProperty<A>
