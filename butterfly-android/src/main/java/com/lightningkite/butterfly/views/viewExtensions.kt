package com.lightningkite.butterfly.views

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.ViewGroup

val Context.activity: Activity?
    get() {
        return when(this){
            is Activity -> this
            is ContextWrapper -> this.baseContext.activity
            else -> null
        }
    }
