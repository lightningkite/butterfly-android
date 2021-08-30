package com.lightningkite.butterfly.observables.binding

import com.lightningkite.butterfly.observables.*
import com.lightningkite.butterfly.time.*
import com.lightningkite.butterfly.views.widget.DateButton
import com.lightningkite.butterfly.views.widget.TimeButton
import com.lightningkite.rxkotlinproperty.android.removed
import com.lightningkite.rxkotlinproperty.subscribeBy
import com.lightningkite.rxkotlinproperty.until
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewString
import java.util.*

/**
 *
 * Binds the date buttons internal date to the ObservableProperty.
 * When the date button updates it will update the observable and vice versa.
 *
 *  Example
 *  val date = StandardObservableProperty(Date())
 *  dateButton.bind(date)
 *
 */

fun DateButton.bind(date: MutableObservableProperty<Date>) {
    date.subscribeBy { it ->
        this.date = it

    }.until(this.removed)
    this.onDateEntered.subscribe { it ->
        date.value = it
    }.until(this.removed)
}


/**
 *
 * Binds the time buttons internal time to the ObservableProperty<Date>.
 * When the time button updates it will update the observables time and vice versa.
 * Also you can specify the minute interval the user can choose from. All other time options
 * outside that interval will be greyed out.
 *
 *  Example
 *  val date = StandardObservableProperty(Date())
 *  timeButton.bind(date, 15)
 *
 */
fun TimeButton.bind(date: MutableObservableProperty<Date>, minuteInterval: Int = 1) {
    this.minuteInterval = minuteInterval
    date.subscribeBy { it ->
        this.date = it
    }.until(this.removed)
    this.onDateEntered.subscribe { it ->
        date.value = it
    }.until(this.removed)
}


/**
 *
 * Binds the date buttons internal time to the ObservableProperty<DateAlone>.
 * When the date button updates it will update the observables date and vice versa.
 *
 *  Example
 *  val dateAlone = StandardObservableProperty(Date().dateAlone)
 *  timeButton.bind(dateAlone)
 *
 */
fun DateButton.bindDateAlone(date: MutableObservableProperty<DateAlone>) {
    date.subscribeBy { it ->
        this.date = dateFrom(it, Date().timeAlone)
    }.until(this.removed)
    this.onDateEntered.subscribe { it ->
        date.value = it.dateAlone
    }.until(this.removed)
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
fun TimeButton.bindTimeAlone(date: MutableObservableProperty<TimeAlone>, minuteInterval: Int = 1) {
    this.minuteInterval = minuteInterval
    date.subscribeBy { it ->
        this.date = dateFrom(Date().dateAlone, it)
    }.until(this.removed)
    this.onDateEntered.subscribe { it ->
        date.value = it.timeAlone
    }.until(this.removed)
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
fun DateButton.bindDateAloneNull(dependency: ActivityAccess, date: MutableObservableProperty<DateAlone?>, startText: ViewString) {
    this.startText = startText.get(dependency)
    date.subscribeBy { it ->
        this.date = it?.let { dateFrom(it, Date().timeAlone) }
    }.until(this.removed)
    this.onDateEntered.subscribe { it ->
        date.value = it.dateAlone
    }.until(this.removed)
}
