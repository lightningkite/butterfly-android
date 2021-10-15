package com.lightningkite.butterfly.views.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import com.lightningkite.butterfly.R
import com.lightningkite.rxkotlinproperty.android.dateSelectorDialog
import com.lightningkite.rxkotlinproperty.android.timeSelectorDialog
import io.reactivex.rxjava3.subjects.PublishSubject
import java.text.DateFormat
import java.util.*


@Deprecated("Use the version from RxKotlin Properties Android instead", replaceWith = ReplaceWith("SwapView", "com.lightningkite.rxkotlinproperty.viewgenerators.SwapView"))
typealias SwapView = com.lightningkite.rxkotlinproperty.viewgenerators.SwapView
@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("VideoPlayer", "com.google.android.exoplayer2.ui.PlayerView"))
typealias VideoPlayer = PlayerView



@Deprecated("Use a regular EditText instead.")
class MultilineEditText : androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}


@Deprecated("Use a regular TextView instead.")
class SelectableText : AppCompatTextView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        this.setTextIsSelectable(true)
    }

}


@Deprecated("Use a regular Progress Bar.")
class HorizontalProgressBar: ProgressBar {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        this.isIndeterminate = false
    }
}

@Deprecated("Use a regular Rating Bar")
class ColorRatingBar : AppCompatRatingBar {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ColorRatingBar, defStyleAttr, 0)
        val progressColor =
            a.getColor(R.styleable.ColorRatingBar_progress_color, Color.YELLOW)
        val emptyColor =
            a.getColor(R.styleable.ColorRatingBar_empty_color, Color.LTGRAY)
        val changeable = a.getBoolean(R.styleable.ColorRatingBar_changeable, true)

        val stars = progressDrawable as LayerDrawable
        // Filled stars
        setRatingStarColor(DrawableCompat.wrap(stars.getDrawable(2)), progressColor)
        // Half filled stars
        setRatingStarColor(DrawableCompat.wrap(stars.getDrawable(1)), Color.TRANSPARENT)
        // Empty stars
        setRatingStarColor(DrawableCompat.wrap(stars.getDrawable(0)), emptyColor)

        setIsIndicator(!changeable)
    }

    private fun setRatingStarColor(drawable: Drawable, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color)
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}


@Deprecated("Use a regular button and bind it with a date through bindTime from rxkotlinproperty or with bindTimeAlone")
class TimeButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatButton(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var minuteInterval: Int = 1

    var format = DateFormat.getTimeInstance(DateFormat.SHORT)

    var date: Date = Date()
        set(value) {
            field = value
            text = format.format(value)
        }
    var onDateEntered = PublishSubject.create<Date>()

    init {
        setOnClickListener {
            context.timeSelectorDialog(date) {
                date = it
                onDateEntered.onNext(it)
            }
        }
    }
}


@Deprecated("Use a regular button and bind it with a date through bindDate from rxkotlinproperty or a DateAlone with bindDateAlone")
class DateButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatButton(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var format = DateFormat.getDateInstance(DateFormat.SHORT)
    var startText = ""

    var date: Date? = null
        set(value) {
            field = value
            text = value?.let { format.format(it) } ?: startText
        }

    var onDateEntered = PublishSubject.create<Date>()

    init {
        setOnClickListener {
            context.dateSelectorDialog(date ?: Date()) {
                date = it
                onDateEntered.onNext(it)
            }
        }
    }
}