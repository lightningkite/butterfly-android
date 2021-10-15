package com.lightningkite.butterfly.views.geometry

import android.graphics.Matrix
import android.graphics.PointF

@Deprecated("Use your own implementation if you really need it")
fun Matrix.mapPoint(point: PointF): PointF {
    val dst = floatArrayOf(0f, 0f)
    val src = floatArrayOf(point.x, point.y)
    mapPoints(dst, src)
    return PointF(dst[0], dst[1])
}

@Deprecated("Use your own implementation if you really need it")
fun Matrix.inverted(): Matrix = Matrix().also { this.invert(it) }
@Deprecated("Use your own implementation if you really need it")
fun Matrix.setInvert(other: Matrix) {
    other.invert(this)
}

@Deprecated("Use your own implementation if you really need it")
inline fun Matrix.setValues(
    a: GFloat,
    b: GFloat,
    c: GFloat,
    d: GFloat,
    e: GFloat,
    f: GFloat
) {
    this.setValues(floatArrayOf(a, b, c, d, e, f, 0f, 0f, 1f))
}