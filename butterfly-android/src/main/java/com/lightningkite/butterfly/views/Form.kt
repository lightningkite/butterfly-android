//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.StandardObservableProperty
import com.lightningkite.butterfly.views.StringResource

class Form {

    companion object {
        var xIsRequired: ViewString = ViewStringRaw("%1\$s is required.")
        var xMustMatchY: ViewString = ViewStringRaw("%1\$s must match %2\$s.")
    }

    val fields: ArrayList<UntypedFormField> = ArrayList()

    fun <T> field(
        name: ViewString,
        defaultValue: T,
        validation: @Escaping() (FormField<T>) -> ViewString?
    ): FormField<T> {
        val obs = StandardObservableProperty(defaultValue)
        val field = FormField(
            name = name,
            observable = obs,
            validation = { untypedField ->
                validation(untypedField as FormField<T>)
            }
        )
        fields.add(field)
        return field
    }

    @JsName("fieldRes")
    fun <T> field(
        name: StringResource,
        defaultValue: T,
        validation: @Escaping() (FormField<T>) -> ViewString?
    ): FormField<T> = field(ViewStringResource(name), defaultValue, validation)

    fun <T> fieldFromProperty(
        name: ViewString,
        property: MutableObservableProperty<T>,
        validation: @Escaping() (FormField<T>) -> ViewString?
    ): FormField<T> {
        val field = FormField(
            name = name,
            observable = property,
            validation = { untypedField ->
                validation(untypedField as FormField<T>)
            }
        )
        fields.add(field)
        return field
    }

    @JsName("fieldFromPropertyRes")
    fun <T> fieldFromProperty(
        name: StringResource,
        property: MutableObservableProperty<T>,
        validation: @Escaping() (FormField<T>) -> ViewString?
    ): FormField<T> = fieldFromProperty(ViewStringResource(name), property, validation)

    fun check(): List<FormValidationError> {
        return fields.mapNotNull { it ->
            val result = checkField(it)
            if (result != null) {
                return@mapNotNull FormValidationError(field = it, string = result)
            } else {
                return@mapNotNull null
            }
        }
    }

    fun runOrDialog(action:()->Unit){
        val errors = check()
        if(errors.isNotEmpty()){
            showDialog(errors.map { it -> it.string }.joinToViewString())
        } else {
            action()
        }
    }

    fun checkField(field: UntypedFormField): ViewString? {
        val result = field.validation(field)
        field.error.value = result
        return result
    }
}
