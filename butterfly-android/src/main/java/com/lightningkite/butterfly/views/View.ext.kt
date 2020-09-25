package com.lightningkite.butterfly.views

import android.view.View
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.*

var View.backgroundDrawable: Drawable?
    get() = this.background
    set(value) {
        this.background = value
    }

var View.backgroundResource: Int
    get() = 0
    set(value) {
        this.setBackgroundResource(value)
    }

fun View.onClick(disabledMilliseconds: Long = 500, action: () -> Unit) {
    var lastActivated = System.currentTimeMillis()
    setOnClickListener {
        if(System.currentTimeMillis() - lastActivated > disabledMilliseconds) {
            action()
            lastActivated = System.currentTimeMillis()
        }
    }
}

fun View.onLongClick(action: () -> Unit) {
    setOnLongClickListener { action(); true }
}


fun View.setBackgroundColorResource(color: ColorResource) {
    setBackgroundResource(color)
}


private val View_focusAtStartup = WeakHashMap<View, Boolean>()
var View.focusAtStartup: Boolean
    get() = View_focusAtStartup[this] ?: true
    set(value) {
        View_focusAtStartup[this] = value
    }