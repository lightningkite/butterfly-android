package com.lightningkite.butterfly.views.geometry

import android.graphics.Matrix
import android.graphics.PointF

fun Matrix.mapPoint(point: PointF): PointF {
    val dst = floatArrayOf(0f, 0f)
    val src = floatArrayOf(point.x, point.y)
    mapPoints(dst, src)
    return PointF(dst[0], dst[1])
}

fun Matrix.inverted(): Matrix = Matrix().also { this.invert(it) }
fun Matrix.setInvert(other: Matrix) {
    other.invert(this)
}

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