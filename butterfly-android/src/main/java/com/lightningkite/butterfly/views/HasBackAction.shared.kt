package com.lightningkite.butterfly.views

interface HasBackAction {
    fun onBackPressed(): Boolean = false
}