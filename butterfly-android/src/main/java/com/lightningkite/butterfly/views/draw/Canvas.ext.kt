package com.lightningkite.butterfly.views.draw

import android.graphics.*
import com.lightningkite.butterfly.views.geometry.Align
import com.lightningkite.butterfly.views.geometry.AlignPair
import com.lightningkite.butterfly.views.geometry.GFloat

fun Canvas.drawTextCentered(text: String, centerX: GFloat, centerY: GFloat, paint: Paint) {
    val textWidth = paint.measureText(text)
    val textHeightOffset = paint.fontMetrics.let { it.ascent + it.descent } / 2
    drawText(text, centerX - textWidth / 2, centerY - textHeightOffset, paint)
}
fun Canvas.drawText(text: String, x: GFloat, y: GFloat, gravity: AlignPair, paint: Paint) {
    val textWidth = paint.measureText(text)
    drawText(
        text,
        when(gravity.horizontal){
            Align.start -> x
            Align.fill, Align.center -> x - textWidth/2
            Align.end -> x - textWidth
        },
        when(gravity.vertical){
            Align.start -> y - paint.fontMetrics.ascent
            Align.fill, Align.center -> y - paint.fontMetrics.let { it.ascent + it.descent } / 2
            Align.end -> y - paint.fontMetrics.descent
        },
        paint
    )
}


var tempRect: RectF = RectF()
fun Canvas.drawBitmap(bitmap: Bitmap, left: GFloat, top: GFloat, right: GFloat, bottom: GFloat){
    tempRect.left = left
    tempRect.top = top
    tempRect.right = right
    tempRect.bottom = bottom
    drawBitmap(bitmap, null, tempRect, null)
}

