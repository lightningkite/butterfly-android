package com.lightningkite.butterfly.views.draw

import android.graphics.Paint
import android.graphics.Shader
import com.lightningkite.butterfly.views.geometry.GFloat

@Deprecated("Use your own implementation if you really need it")
val Paint.textHeight: GFloat get() = fontMetrics.let { it.descent - it.ascent }
