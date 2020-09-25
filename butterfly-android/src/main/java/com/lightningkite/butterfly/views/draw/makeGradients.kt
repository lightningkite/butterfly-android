package com.lightningkite.butterfly.views.draw

import android.graphics.Shader
import com.lightningkite.butterfly.views.geometry.GFloat


fun newRadialGradient(
    centerX: GFloat,
    centerY: GFloat,
    radius: GFloat,
    colors: List<Int>,
    stops: List<GFloat>,
    tile: Shader.TileMode
) = android.graphics.RadialGradient(
    centerX,
    centerY,
    radius,
    colors.toIntArray(),
    stops.toFloatArray(),
    tile
)

fun newLinearGradient(
    x0: GFloat,
    y0: GFloat,
    x1: GFloat,
    y1: GFloat,
    colors: List<Int>,
    positions: List<GFloat>,
    tile: Shader.TileMode
) = android.graphics.LinearGradient(
    x0,
    y0,
    x1,
    y1,
    colors.toIntArray(),
    positions.toFloatArray(),
    tile
)
