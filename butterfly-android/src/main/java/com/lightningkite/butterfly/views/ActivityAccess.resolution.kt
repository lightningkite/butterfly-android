package com.lightningkite.butterfly.views


import android.util.DisplayMetrics
import com.lightningkite.butterfly.*
import com.lightningkite.rxkotlinproperty.android.ColorResource
import com.lightningkite.rxkotlinproperty.android.StringResource
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import java.util.*

fun ActivityAccess.getString(resource: StringResource): String = context.getString(resource)
fun ActivityAccess.getColor(resource: ColorResource): Int = context.resources.getColor(resource)
val ActivityAccess.displayMetrics: DisplayMetrics get() = context.resources.displayMetrics


