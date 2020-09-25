package com.lightningkite.butterfly.views

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import java.util.*

fun EditText.setOnDoneClick(action: () -> Unit) {
    this.setOnEditorActionListener { v, actionId, event ->
        action()
        true
    }
}