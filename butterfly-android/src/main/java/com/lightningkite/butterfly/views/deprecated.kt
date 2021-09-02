package com.lightningkite.butterfly.views

import android.R
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.widget.Spinner
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.rxkotlinproperty.viewgenerators.*
import com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewString
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception



@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ActivityAccess", "com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess"))
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

@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ViewGenerator", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator"))
typealias ViewGenerator = com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator
@Deprecated("Use directly from RxKotlin Property instead", ReplaceWith("ViewGenerator.Default", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator"))
typealias DefaultViewGenerator = ViewGenerator.Default
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewString", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewString"))
typealias ViewString = com.lightningkite.rxkotlinproperty.viewgenerators.ViewString
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringRaw", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringRaw"))
typealias ViewStringRaw = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringRaw
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringResource", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringResource"))
typealias ViewStringResource = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringResource
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringTemplate", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringTemplate"))
typealias ViewStringTemplate = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringTemplate
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringComplex", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringComplex"))
typealias ViewStringComplex = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringComplex
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("ViewStringList", "com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringList"))
typealias ViewStringList = com.lightningkite.rxkotlinproperty.viewgenerators.ViewStringList
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("DialogRequest", "com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest"))
typealias DialogRequest = com.lightningkite.rxkotlinproperty.viewgenerators.DialogRequest
@Deprecated("Use directly from RxKotlin Property View Generators", replaceWith = ReplaceWith("EntryPoint", "com.lightningkite.rxkotlinproperty.viewgenerators.EntryPoint"))
typealias EntryPoint = com.lightningkite.rxkotlinproperty.viewgenerators.EntryPoint
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("StringResource", "com.lightningkite.rxkotlinproperty.android.StringResource"))
typealias StringResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("ColorResource", "com.lightningkite.rxkotlinproperty.android.ColorResource"))
typealias ColorResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("DrawableResource", "com.lightningkite.rxkotlinproperty.android.DrawableResource"))
typealias DrawableResource = Int
@Deprecated("Use the version from RxKotlin Properties Android instead.", replaceWith = ReplaceWith("HasBackAction", "com.lightningkite.rxkotlinproperty.viewgenerators.HasBackAction"))
typealias HasBackAction = com.lightningkite.rxkotlinproperty.viewgenerators.HasBackAction
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("showDialog", "com.lightningkite.rxkotlinproperty.viewgenerators.showDialog"))
fun showDialog(request: DialogRequest) = new_showDialog(request)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("showDialog", "com.lightningkite.rxkotlinproperty.viewgenerators.showDialog"))
fun showDialog(message: ViewString) = new_showDialog(message)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("spinnerTextColor", "com.lightningkite.rxkotlinproperty.android.spinnerTextColor"))
var Spinner.spinnerTextColor: Int get() = this.new_spinnerTextColor; set(value) { this.new_spinnerTextColor = value }
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("spinnerTextSize", "com.lightningkite.rxkotlinproperty.android.spinnerTextSize"))
var Spinner.spinnerTextSize: Double get() = this.new_spinnerTextSize; set(value) {this.new_spinnerTextSize = value}
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("joinToViewString", "com.lightningkite.rxkotlinproperty.viewgenerators.joinToViewString"))
fun List<ViewString>.joinToViewString(separator: String = "\n"): ViewString = this.new_joinToViewString(separator)