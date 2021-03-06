package com.lightningkite.butterfly.views

import android.graphics.Color

typealias ColorValue = Int

fun Int.asColor(): ColorValue = this
fun Long.asColor(): ColorValue = this.toInt()
fun colorValue(value: Long): ColorValue = value.toInt()


fun ColorValue.colorAlpha(desiredAlpha: Int): ColorValue = Color.argb(
    desiredAlpha,
    Color.red(this),
    Color.green(this),
    Color.blue(this)
)
