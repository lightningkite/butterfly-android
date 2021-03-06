//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables

import com.lightningkite.butterfly.AnyObject
import com.lightningkite.butterfly.Box
import com.lightningkite.butterfly.JsName
import com.lightningkite.butterfly.boxWrap
import com.lightningkite.butterfly.views.HasBackAction
import io.reactivex.subjects.PublishSubject

class ObservableStack<T : AnyObject> : ObservableProperty<List<T>>() {

    companion object {
        fun <T: AnyObject> withFirst(value: T): ObservableStack<T> {
            val result = ObservableStack<T>()
            result.reset(value)
            return result
        }
    }

    override val onChange: PublishSubject<Box<List<T>>> = PublishSubject.create()
    override val value: List<T>
        get() {
            return stack
        }

    val stack: ArrayList<T> = ArrayList<T>()

    fun push(t: T) {
        stack.add(t)
        onChange.onNext(boxWrap(stack))
    }

    fun swap(t: T) {
        stack.removeAt(stack.lastIndex)
        stack.add(t)
        onChange.onNext(boxWrap(stack))
    }

    fun pop(): Boolean {
        if (stack.size <= 1) {
            return false
        }
        stack.removeAt(stack.lastIndex)
        onChange.onNext(boxWrap(stack))
        return true
    }

    fun dismiss(): Boolean {
        if (stack.isEmpty()) {
            return false
        }
        stack.removeAt(stack.lastIndex)
        onChange.onNext(boxWrap(stack))
        return true
    }

    fun backPressPop(): Boolean {
        val last = stack.lastOrNull()
        if(last is HasBackAction && last.onBackPressed()) return true
        return pop()
    }

    fun backPressDismiss(): Boolean {
        val last = stack.lastOrNull()
        if(last is HasBackAction && last.onBackPressed()) return true
        return dismiss()
    }

    fun popTo(t: T) {
        var found = false
        for (i in 0..stack.lastIndex) {
            if (found) {
                stack.removeAt(stack.lastIndex)
            } else if (stack[i] === t) {
                found = true
            }
        }
        onChange.onNext(boxWrap(stack))
    }

    @JsName("popToPredicate")
    fun popTo(predicate: (T) -> Boolean) {
        var found = false
        for (i in 0..stack.lastIndex) {
            if (found) {
                stack.removeAt(stack.lastIndex)
            } else if (predicate(stack[i])) {
                found = true
            }
        }
        onChange.onNext(boxWrap(stack))
    }

    fun root() {
        popTo(t = stack.first())
    }

    fun reset(t: T) {
        stack.clear()
        stack.add(t)
        onChange.onNext(boxWrap(stack))
    }
}
