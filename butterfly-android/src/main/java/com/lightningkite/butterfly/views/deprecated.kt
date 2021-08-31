package com.lightningkite.butterfly.views

import android.R
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


@Deprecated("Use directly from RxKotlin property instead", ReplaceWith("ViewGenerator", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator"))
typealias ViewGenerator = com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator


@Deprecated("Use directly instead", ReplaceWith("ActivityAccess", "com.lightningkite.butterfly.android.ActivityAccess"))
typealias ViewDependency = ActivityAccess

@Deprecated("")
fun ActivityAccess.downloadDrawable(
    url: String,
    width: Int? = null,
    height: Int? = null,
    onResult: (Drawable?) -> Unit
) {
    Picasso.get()
            .load(url)
            .let {
                if (width == null || height == null) it
                else it.resize(width.coerceAtLeast(100), height.coerceAtLeast(100)).centerCrop()
            }
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    onResult(null)
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    onResult(BitmapDrawable(bitmap))
                }
            })
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

@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringTemplate"))
typealias ViewStringTemplate = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringTemplate
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringResource"))
typealias ViewStringResource = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringResource
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.StringResource"))
typealias StringResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.ColorResource"))
typealias ColorResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.DrawableResource"))
typealias DrawableResource = Int
