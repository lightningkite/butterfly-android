package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.AnyObject
import com.lightningkite.butterfly.weak
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

@Deprecated("Use RX directly instead", ReplaceWith("Observable<Element>", "io.reactivex.Observable"))
typealias Event<Element> = Observable<Element>

@Deprecated("Use RX directly instead", ReplaceWith("Subject<Element>", "io.reactivex.subjects.Subject"))
typealias InvokableEvent<Element> = Subject<Element>

@Deprecated("Use RX directly instead", ReplaceWith("Subject<Element>", "io.reactivex.subjects.PublishSubject"))
typealias StandardEvent<Element> = PublishSubject<Element>
