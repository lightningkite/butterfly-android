package com.lightningkite.butterfly

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


typealias AnyObject = Any
typealias AnyEquatable = Any
typealias AnyHashable = Any
typealias IsEquatable = Any
typealias IsHashable = Any
interface Hashable {}
interface Equatable {}
typealias SomeEnum = Enum<*>

@Target(AnnotationTarget.FILE)
annotation class SharedCode

@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPE,
    AnnotationTarget.FILE,
    AnnotationTarget.TYPEALIAS
)
annotation class JsName(val name: String)

@Target(AnnotationTarget.TYPE)
annotation class Escaping
@Deprecated("Capitalize the name please!", ReplaceWith("Escaping", "com.lightningkite.butterfly.Escaping"))
typealias escaping = Escaping

@Target(AnnotationTarget.CLASS)
annotation class SwiftMustBeClass
@Deprecated("Capitalize the name please!", ReplaceWith("SwiftMustBeClass", "com.lightningkite.butterfly.SwiftMustBeClass"))
typealias swiftMustBeClass = SwiftMustBeClass

@Target(AnnotationTarget.PROPERTY)
annotation class Unowned
@Deprecated("Capitalize the name please!", ReplaceWith("Unowned", "com.lightningkite.butterfly.Unowned"))
typealias unowned = Unowned

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Modifies
@Deprecated("Capitalize the name please!", ReplaceWith("Modifies", "com.lightningkite.butterfly.Modifies"))
typealias modifies = Modifies

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class SwiftName(val name: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class UnownedSelf
@Deprecated("Capitalize the name please!", ReplaceWith("UnownedSelf", "com.lightningkite.butterfly.UnownedSelf"))
typealias unownedSelf = UnownedSelf

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class WeakSelf
@Deprecated("Capitalize the name please!", ReplaceWith("WeakSelf", "com.lightningkite.butterfly.WeakSelf"))
typealias weakSelf = WeakSelf

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class CaptureUnowned(vararg val keys: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class CaptureWeak(vararg val keys: String)

@Target(AnnotationTarget.TYPE)
annotation class SwiftExactly(val parameterName: String = "default")
@Deprecated("Capitalize the name please!", ReplaceWith("SwiftExactly", "com.lightningkite.butterfly.SwiftExactly"))
typealias swiftExactly = SwiftExactly

@Target(AnnotationTarget.TYPE)
annotation class SwiftDescendsFrom(val parameterName: String = "default")
@Deprecated("Capitalize the name please!", ReplaceWith("SwiftDescendsFrom", "com.lightningkite.butterfly.SwiftDescendsFrom"))
typealias swiftDescendsFrom = SwiftDescendsFrom

@Target(AnnotationTarget.FUNCTION)
annotation class DiscardableResult
@Deprecated("Capitalize the name please!", ReplaceWith("DiscardableResult", "com.lightningkite.butterfly.DiscardableResult"))
typealias discardableResult = DiscardableResult

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class SwiftReturnType(val text: String)
@Deprecated("Capitalize the name please!", ReplaceWith("SwiftReturnType", "com.lightningkite.butterfly.SwiftReturnType"))
typealias swiftReturnType = SwiftReturnType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class SwiftExtensionWhere(val text: String)

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE
)
annotation class PlatformSpecific

class WeakPropertyDelegate<T>(initial: T) {
    var ref = WeakReference<T>(initial)
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return ref.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value != null) {
            ref = WeakReference(value)
        }
    }
}

fun <T> weak(value: T) = WeakPropertyDelegate(value)

fun fatalError(reason: String = ""): Nothing {
    throw Error(reason)
}