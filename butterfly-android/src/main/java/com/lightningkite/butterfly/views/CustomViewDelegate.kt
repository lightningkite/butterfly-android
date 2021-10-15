//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.views

import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.View
import com.lightningkite.butterfly.views.widget.CustomView
import com.lightningkite.butterfly.views.geometry.GFloat
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class CustomViewDelegate {
    var customView: CustomView? = null
    abstract fun generateAccessibilityView(): View?
    abstract fun draw(canvas: Canvas, width: GFloat, height: GFloat, displayMetrics: DisplayMetrics)
    open fun onTouchDown(id: Int, x: GFloat, y: GFloat, width: GFloat, height: GFloat): Boolean = false
    open fun onTouchMove(id: Int, x: GFloat, y: GFloat, width: GFloat, height: GFloat): Boolean = false
    open fun onTouchCancelled(id: Int, x: GFloat, y: GFloat, width: GFloat, height: GFloat): Boolean = false
    open fun onTouchUp(id: Int, x: GFloat, y: GFloat, width: GFloat, height: GFloat): Boolean = false
    open fun onWheel(delta: Float): Boolean = false
    open fun sizeThatFitsWidth(width: GFloat, height: GFloat): GFloat = width
    open fun sizeThatFitsHeight(width: GFloat, height: GFloat): GFloat = height

    fun invalidate() { customView?.invalidate() }
    fun postInvalidate() { customView?.postInvalidate() }

    val toDispose = CompositeDisposable()
    private var _removed: CompositeDisposable? = null
    val removed: CompositeDisposable get() = _removed!!
    init {
        _removed = toDispose
    }
    fun dispose() {
        toDispose.dispose()
    }
}
