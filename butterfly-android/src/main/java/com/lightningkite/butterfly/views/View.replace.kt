package com.lightningkite.butterfly.views

import android.view.View
import android.view.ViewGroup

fun View.replace(other: View) {
    val parent = (this.parent as ViewGroup)
    other.layoutParams = this.layoutParams
    val index = parent.indexOfChild(this)
    parent.removeView(this)
    parent.addView(other, index)
}