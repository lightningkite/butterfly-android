package com.lightningkite.butterfly.rx

import android.view.View
import com.lightningkite.butterfly.AnyObject
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.butterfly.weak
import com.lightningkite.rxkotlinproperty.DisposeCondition
import com.lightningkite.rxkotlinproperty.MutableProperty
import com.lightningkite.rxkotlinproperty.android.removed
import com.lightningkite.rxkotlinproperty.combineLatest
import com.lightningkite.rxkotlinproperty.until
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

fun Disposable.solvePrivateDisposal(items: List<Any>) { for(item in items){ if(item is View){ this.until(item.removed) } } }

@Deprecated("Just use RX stuff")
fun <Element: Any> Observable<Element>.add(listener: @Escaping() (Element) -> Boolean): Disposable {
    var disposable: Disposable? = null
    val disp = this.subscribeBy(onNext = { item ->
        if (listener(item)) {
            disposable?.dispose()
        }
    })
    disposable = disp
    return disp
}

@Deprecated("Just use RX disposal stuff")
fun <A: AnyObject, Element: Any> Observable<Element>.addWeak(referenceA: A, listener: @Escaping() (A, Element) -> Unit): Disposable {
    var disposable: Disposable? = null
    val weakA: A? by weak(referenceA)
    val disp = this.subscribeBy(onNext = { item ->
        val a = weakA
        if (a != null) {
            listener(a, item)
        } else {
            disposable?.dispose()
        }
    })
    disposable = disp
    disp.solvePrivateDisposal(listOf(referenceA))
    return disp
}

@Deprecated("Just use RX disposal stuff")
fun <A: AnyObject, B: AnyObject, Element: Any> Observable<Element>.addWeak(referenceA: A, referenceB: B, listener: @Escaping() (A, B, Element) -> Unit): Disposable {
    var disposable: Disposable? = null
    val weakA: A? by weak(referenceA)
    val weakB: B? by weak(referenceB)
    val disp = this.subscribeBy(onNext = { item ->
        val a = weakA
        val b = weakB
        if (a != null && b != null) {
            listener(a, b, item)
        } else {
            disposable?.dispose()
        }
    })
    disposable = disp
    disp.solvePrivateDisposal(listOf(referenceA, referenceB))
    return disp
}


@Deprecated("Just use RX disposal stuff")
fun <A: AnyObject, B: AnyObject, C: AnyObject, Element: Any> Observable<Element>.addWeak(referenceA: A, referenceB: B, referenceC: C, listener: @Escaping() (A, B, C, Element) -> Unit): Disposable {
    var disposable: Disposable? = null
    val weakA: A? by weak(referenceA)
    val weakB: B? by weak(referenceB)
    val weakC: C? by weak(referenceC)
    val disp = this.subscribeBy(onNext = { item ->
        val a = weakA
        val b = weakB
        val c = weakC
        if (a != null && b != null && c != null) {
            listener(a, b, c, item)
        } else {
            disposable?.dispose()
        }
    })
    disp.solvePrivateDisposal(listOf(referenceA, referenceB, referenceC))
    disposable = disp
    return disp
}

@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("DisposeCondition", "com.lightningkite.rxkotlinproperty.DisposeCondition"))
typealias DisposeCondition = com.lightningkite.rxkotlinproperty.DisposeCondition
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("DisposableLambda", "com.lightningkite.rxkotlinproperty.DisposableLambda"))
typealias DisposableLambda = com.lightningkite.rxkotlinproperty.DisposableLambda
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("forever", "com.lightningkite.rxkotlinproperty.forever"))
fun <T : Disposable> T.forever(): T { return this }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("until", "com.lightningkite.rxkotlinproperty.until"))
fun <T : Disposable> T.until(condition: DisposeCondition): T { condition.call(this); return this }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("removed", "com.lightningkite.rxkotlinproperty.android.removed"))
val View.removed:DisposeCondition get() = new_removed
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("working", "com.lightningkite.rxkotlinproperty.rx.working"))
fun <Element : Any> Single<Element>.working(observable: MutableProperty<Boolean>): Single<Element> = this.new_working(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("mapNotNull", "com.lightningkite.rxkotlinproperty.mapNotNull"))
fun <Element, Destination : Any> Observable<Element>.mapNotNull(transform: (Element) -> Destination?): Observable<Destination> = this.new_mapNotNull(transform)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("zip", "com.lightningkite.rxkotlinproperty.zip"))
fun <IN : Any> List<Single<IN>>.zip(): Single<List<IN>> = this.new_zip()
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combineLatest", "com.lightningkite.rxkotlinproperty.combineLatest"))
fun <Element : Any, R : Any, OUT : Any> Observable<Element>.combineLatest(observable: Observable<R>, function: (Element, R) -> OUT): Observable<OUT> = this.new_combineLatest(observable, function)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combineLatest", "com.lightningkite.rxkotlinproperty.combineLatest"))
fun <IN : Any, OUT : Any> List<Observable<IN>>.combineLatest(combine: (List<IN>) -> OUT): Observable<OUT> = this.new_combineLatest(combine)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("combineLatest", "com.lightningkite.rxkotlinproperty.combineLatest"))
fun <IN : Any> List<Observable<IN>>.combineLatest(): Observable<List<IN>> = this.new_combineLatest()