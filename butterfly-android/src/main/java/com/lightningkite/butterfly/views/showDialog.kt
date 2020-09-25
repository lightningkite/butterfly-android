//! This file is Khrysalis compatible.
package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.JsName
import com.lightningkite.butterfly.observables.StandardEvent
import com.lightningkite.butterfly.observables.StandardObservableProperty
import io.reactivex.subjects.PublishSubject

val lastDialog = StandardObservableProperty<DialogRequest?>(null)
val showDialogEvent: PublishSubject<DialogRequest> = PublishSubject.create()

class DialogRequest(
    val string: ViewString,
    val confirmation: (()->Unit)? = null
)

fun showDialog(request: DialogRequest) {
    lastDialog.value = request
    showDialogEvent.onNext(request)
}

@JsName("showDialogAlert")
fun showDialog(message: ViewString) {
    showDialog(DialogRequest(string = message))
}
