package com.lightningkite.butterfly.views

import android.R
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import com.lightningkite.butterfly.android.ActivityAccess
import com.lightningkite.butterfly.fatalError


@Deprecated("Use directly instead", ReplaceWith("ActivityAccess", "com.lightningkite.butterfly.android.ActivityAccess"))
typealias ViewDependency = ActivityAccess

@Deprecated("")
fun ActivityAccess.downloadDrawable(
    url: String,
    width: Int? = null,
    height: Int? = null,
    onResult: (Drawable?) -> Unit
) {
    fatalError("deprecated")
}

@Deprecated("")
fun ActivityAccess.checkedDrawable(
    checked: Drawable,
    normal: Drawable
) = StateListDrawable().apply {
    addState(intArrayOf(R.attr.state_checked), checked)
    addState(intArrayOf(), normal)
}

@Deprecated("")
fun ActivityAccess.setSizeDrawable(drawable: Drawable, width: Int, height: Int): Drawable {
    val scale = context.resources.displayMetrics.density
    return object : LayerDrawable(arrayOf(drawable)) {
        override fun getIntrinsicWidth(): Int = (width * scale).toInt()
        override fun getIntrinsicHeight(): Int = (height * scale).toInt()
    }
}
