//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.views.StringResource
import com.lightningkite.butterfly.views.getString


interface ViewString {
    fun get(dependency: ActivityAccess): String
}

class ViewStringRaw(val string: String) : ViewString {
    override fun get(dependency: ActivityAccess): String = string
}

class ViewStringResource(val resource: StringResource) : ViewString {
    override fun get(dependency: ActivityAccess): String = dependency.getString(resource)
}

class ViewStringTemplate(val template: ViewString, val arguments: List<Any>) : ViewString {
    override fun get(dependency: ActivityAccess): String {
        val templateResolved = template.get(dependency)
        val fixedArguments = arguments.map { it -> (it as? ViewString)?.get(dependency) ?: it }
        return templateResolved.formatList(fixedArguments)
    }
}

class ViewStringComplex(val getter: @Escaping() (ActivityAccess) -> String) : ViewString {
    override fun get(dependency: ActivityAccess): String = getter(dependency)
}

class ViewStringList(val parts: List<ViewString>, val separator: String = "\n"): ViewString {
    override fun get(dependency: ActivityAccess): String {
        return parts.joinToString(separator) { it -> it.get(dependency) }
    }
}

fun List<@SwiftExactly("Element") ViewString>.joinToViewString(separator: String = "\n"): ViewString {
    if(this.size == 1){
        return this.first()
    }
    return ViewStringList(this, separator)
}

fun ViewString.toDebugString(): String {
    val thing = this
    when (thing) {
        is ViewStringRaw -> return thing.string
        is ViewStringResource -> return thing.resource.toString()
        is ViewStringTemplate -> return thing.template.toDebugString() + "(" + thing.arguments.joinToString { it ->
            if(it is ViewString)
                return@joinToString it.toDebugString()
            else
                return@joinToString "$it"
        } + ")"
        is ViewStringList -> return thing.parts.joinToString(thing.separator) { it -> it.toDebugString() }
        is ViewStringComplex -> return "<Complex string $thing>"
        else -> return "Unknown"
    }
}
