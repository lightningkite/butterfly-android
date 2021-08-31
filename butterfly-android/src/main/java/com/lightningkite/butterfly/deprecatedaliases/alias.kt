package com.lightningkite.butterfly.deprecatedaliases

import android.view.View
import android.widget.TextView
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.rxkotlinproperty.*
import com.lightningkite.rxkotlinproperty.android.bindExists
import com.lightningkite.rxkotlinproperty.android.bindString
import com.lightningkite.rxkotlinproperty.android.bindVisible
import com.lightningkite.rxkotlinproperty.android.removed
import io.reactivex.Single
import io.reactivex.disposables.Disposable

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.subscribeBy", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.subscribeBy"))
inline fun <T> Property<T>.new_subscribeBy(
    noinline onError: (Throwable) -> Unit = { it -> it.printStackTrace() },
    noinline onComplete: () -> Unit = {},
    crossinline onNext: (T) -> Unit = { it -> }
): Disposable = subscribeBy(onError, onComplete, onNext)


@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.forever", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.forever"))
fun <T : Disposable> T.new_forever(): T {
    return this.forever()
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.until", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.until"))
fun <T : Disposable> T.new_until(condition: DisposeCondition): T {
    return this.until(condition)
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindString", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindString"))
fun TextView.new_bindString(observable:ObservableProperty<String>){
    this.bindString(observable)
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindExists", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindExists"))
fun View.new_bindExists(observable:ObservableProperty<Boolean>){
    this.bindExists(observable)
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindVisible", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindVisible"))
fun View.new_bindVisible(observable:ObservableProperty<Boolean>){
    this.bindVisible(observable)
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.combine", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.combine"))
fun <T, B, C> Property<T>.new_combiner(
    other: Property<B>,
    combiner: (T, B) -> C
): Property<C> {
    return this.combine(other, combiner)
}
@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.removed", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.removed"))
val View.new_removed:DisposeCondition
    get() = removed

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.rx.working", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.rx.working"))
fun <Element : Any> Single<Element>.new_working(observable: MutableProperty<Boolean>): Single<Element> = this.working(observable)