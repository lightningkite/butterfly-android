package com.lightningkite.butterfly.views

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.DisplayMetrics
import android.view.View
import com.lightningkite.butterfly.observables.*
import com.lightningkite.butterfly.rx.forever
import com.lightningkite.butterfly.time.*
import com.lightningkite.butterfly.views.geometry.GFloat
import com.lightningkite.butterfly.weakSelf
import io.reactivex.rxkotlin.subscribeBy

/**Renders a swipeable calendar.**/
open class SelectDateMonthCVD : MonthCVD() {
    override fun generateAccessibilityView(): View? = null

    var selected: MutableObservableProperty<DateAlone?> = StandardObservableProperty(null)

    init {
        selected.value?.let {
            this.currentMonthObs.value = it.dayOfMonth(1)
        }
        this.selected.onChange.subscribeBy @weakSelf { value ->
            this?.invalidate()
        }.forever()
    }

    val selectedDayPaint: Paint = Paint()
    val selectedPaint: Paint = Paint()

    override fun drawDay(
        canvas: Canvas,
        showingMonth: DateAlone,
        day: DateAlone,
        displayMetrics: DisplayMetrics,
        outer: RectF,
        inner: RectF
    ) {
        when {
            day == selected.value -> {
                CalendarDrawing.dayBackground(canvas, inner, selectedPaint)
                CalendarDrawing.day(canvas, showingMonth, day, inner, selectedDayPaint)
            }
            else -> {
                CalendarDrawing.day(canvas, showingMonth, day, inner, dayPaint)
            }
        }
    }

    override fun measure(width: GFloat, height: GFloat, displayMetrics: DisplayMetrics) {
        super.measure(width = width, height = height, displayMetrics = displayMetrics)
        selectedDayPaint.textSize = dayPaint.textSize
    }

    override fun onTap(day: DateAlone) {
        selected.value = day
    }
}
