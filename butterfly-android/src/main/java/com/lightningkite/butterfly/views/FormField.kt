//! This file is Khrysalis compatible.
package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.StandardObservableProperty
import com.lightningkite.butterfly.views.StringResource


class FormValidationError(
    val field: UntypedFormField,
    val string: ViewString
)

interface UntypedFormField {
    val name: ViewString
    val untypedObservable: Any
    val validation: (UntypedFormField) -> ViewString?
    val error: StandardObservableProperty<ViewString?>
}

class FormField<T>(
    override val name: ViewString,
    val observable: MutableObservableProperty<T>,
    override val validation: @Escaping() (UntypedFormField) -> ViewString?
) : UntypedFormField {
    override val error: StandardObservableProperty<ViewString?> = StandardObservableProperty(null)
    var value: T
        get() = observable.value
        set(value) {
            observable.value = value
        }
    override val untypedObservable: Any
        get() = observable
}

fun FormField<String>.required(): ViewString? {
    if (this.observable.value.isBlank()) {
        return ViewStringTemplate(Form.xIsRequired, listOf(this.name))
    } else {
        return null
    }
}

fun <T> FormField<T>.notNull(): ViewString? {
    if (this.observable.value == null) {
        return ViewStringTemplate(Form.xIsRequired, listOf(this.name))
    } else {
        return null
    }
}

fun FormField<Boolean>.notFalse(): ViewString? {
    if (!this.observable.value) {
        return ViewStringTemplate(Form.xIsRequired, listOf(this.name))
    } else {
        return null
    }
}

fun ViewString.unless(condition: Boolean): ViewString? {
    if (condition) {
        return null
    } else {
        return this
    }
}


fun <T : AnyEquatable> FormField<T>.matches(other: FormField<T>): ViewString? {
    if (this.observable.value != other.observable.value) {
        return ViewStringTemplate(Form.xMustMatchY, listOf(this.name, other.name))
    } else {
        return null
    }
}

