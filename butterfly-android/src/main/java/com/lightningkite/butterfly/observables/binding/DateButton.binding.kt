package com.lightningkite.butterfly.observables.binding

import android.widget.Button
import com.lightningkite.butterfly.observables.*
import com.lightningkite.butterfly.time.*
import com.lightningkite.butterfly.views.widget.DateButton
import com.lightningkite.butterfly.views.widget.TimeButton
import com.lightningkite.rxkotlinproperty.MutableProperty
import com.lightningkite.rxkotlinproperty.android.*
import com.lightningkite.rxkotlinproperty.android.resources.ViewString
import com.lightningkite.rxkotlinproperty.subscribeBy
import com.lightningkite.rxkotlinproperty.until
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



/**
 *
 * Binds button text to the formatted DateAlone from the MutableProperty.
 * When the date button updates it will update the observables date and vice versa.
 *
 *  Example
 *  val dateAlone = StandardProperty(Date().dateAlone)
 *  button.bindDateAlone(dateAlone)
 *
 */
fun Button.bindDateAlone(date: MutableProperty<DateAlone>) {
    date.subscribeBy {
        this.text = it.format(ClockPartSize.Short)
    }.until(removed)
    this.setOnClickListener {
        context.dateSelectorDialog(dateFrom(date.value, Date().timeAlone)) {
            date.value = it.dateAlone
        }
    }
}


/**
 *
 * Binds the time buttons internal time to the ObservableProperty<TimeAlone>.
 * When the time button updates it will update the observables time and vice versa.
 * Also you can specify the minute interval the user can choose from. All other time options
 * outside that interval will be greyed out.
 *
 *  Example
 *  val time = StandardObservableProperty(Date().timeAlone)
 *  timeButton.bind(time, 15)
 *
 */
fun Button.bindTimeAlone(time: MutableProperty<TimeAlone>, minuteInterval: Int = 1) {
    time.subscribeBy { it ->
        this.text = it.format(ClockPartSize.Short)
    }.until(this.removed)
    this.setOnClickListener {
        context.timeSelectorDialog(dateFrom(Date().dateAlone, time.value), minuteInterval) {
            time.value = it.timeAlone
        }
    }
}

/**
 *
 * Binds the date buttons internal time to the ObservableProperty<DateAlone?>.
 * When the date button updates it will update the observables date and vice versa.
 *
 *  Example
 *  val dateAloneNullable = StandardObservableProperty(Date().dateAlone)
 *  timeButton.bind(dateAlone)
 *
 */
fun Button.bindDateAlone(date: MutableProperty<DateAlone?>, nullText: String) {
    date.subscribeBy { it ->
        this.text = it?.format(ClockPartSize.Short) ?: nullText
    }.until(this.removed)
    this.setOnClickListener {
        context.dateSelectorDialog(date.value?.let{dateFrom(it, Date().timeAlone)} ?: Date()) {
            date.value = it.dateAlone
        }
    }
}


/**
 *
 * Binds the time buttons internal time to the ObservableProperty<TimeAlone>.
 * When the time button updates it will update the observables time and vice versa.
 * Also you can specify the minute interval the user can choose from. All other time options
 * outside that interval will be greyed out.
 *
 *  Example
 *  val time = StandardObservableProperty(Date().timeAlone)
 *  timeButton.bind(time, 15)
 *
 */
fun Button.bindTimeAlone(time: MutableProperty<TimeAlone?>, minuteInterval: Int = 1, nullText:String) {
    time.subscribeBy { it ->
        this.text = it?.format(ClockPartSize.Short) ?: nullText
    }.until(this.removed)
    this.setOnClickListener {
        context.timeSelectorDialog(time.value?.let{dateFrom(Date().dateAlone, it)} ?: Date(), minuteInterval) {
            time.value = it.timeAlone
        }
    }
}
