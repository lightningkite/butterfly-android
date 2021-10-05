package com.lightningkite.butterfly.views

import android.view.View
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.lightningkite.butterfly.deprecatedaliases.new_focusAtStartup
import com.lightningkite.rxkotlinproperty.android.ColorResource
import java.util.*


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

