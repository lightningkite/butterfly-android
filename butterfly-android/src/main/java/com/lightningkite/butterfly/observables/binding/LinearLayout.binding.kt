//! This file will translate using Khrysalis.
package com.lightningkite.butterfly.observables.binding

import android.view.View
import android.widget.LinearLayout
import com.lightningkite.butterfly.Escaping
import com.lightningkite.butterfly.observables.*
import com.lightningkite.butterfly.rx.removed
import com.lightningkite.butterfly.rx.until
import com.lightningkite.butterfly.views.geometry.AlignPair
import com.lightningkite.butterfly.views.params

private class LinearLayoutBoundSubview<T>(val view: View, val property: StandardObservableProperty<T>)

/**
 *
 * Binds all the subviews in the linear layout to the list of data in the observable.
 * makeView is the lambda that will return the view linked to an individual item in the
 * list.
 *
 * Example
 * val data = StandardObservableProperty(listOf(1,2,3,4,5))
 * layout.bind(
 *  data = data,
 *  defaultValue = 0,
 *  makeView = { observable ->
 *       val xml = ViewXml()
 *       val view = xml.setup(dependency)
 *       view.text.bindString(obs.map{it -> it.toString()})
 *       return view
 *       }
 * )
 */

fun <T> LinearLayout.bind(
    data: ObservableProperty<List<T>>,
    defaultValue: T,
    makeView: @Escaping() (ObservableProperty<T>) -> View
) {
    val existingViews: ArrayList<LinearLayoutBoundSubview<T>> = ArrayList()
    data.subscribeBy { value ->
        //Fix view count
        val excessViews = existingViews.size - value.size
        if(excessViews > 0){
            //remove views
            for(iter in 1 .. excessViews) {
                val old = existingViews.removeAt(existingViews.lastIndex)
                this.removeView(old.view)
            }
        } else if(existingViews.size < value.size) {
            //add views
            for(iter in 1 .. (-excessViews)) {
                val prop = StandardObservableProperty(defaultValue)
                val view = makeView(prop)
                this.addView(view, this.params(gravity = AlignPair.centerFill))
                existingViews.add(LinearLayoutBoundSubview(view, prop))
            }
        }

        //Update views
        for(index in value.indices){
            existingViews[index].property.value = value[index]
        }
    }.until(this.removed)
}


/**
 *
 * Binds all the subviews in the linear layout to the list of data in the observable in the horizontal direction.
 * makeView is the lambda that will return the view linked to an individual item in the
 * list.
 *
 * Example
 * val data = StandardObservableProperty(listOf(1,2,3,4,5))
 * layout.bind(
 *  data = data,
 *  defaultValue = 0,
 *  makeView = { observable ->
 *       val xml = ViewXml()
 *       val view = xml.setup(dependency)
 *       view.text.bindString(obs.map{it -> it.toString()})
 *       return view
 *       }
 * )
 */
fun <T> LinearLayout.bindHorizontal(
    data: ObservableProperty<List<T>>,
    defaultValue: T,
    makeView: @Escaping() (ObservableProperty<T>) -> View
) {
    val existingViews: ArrayList<LinearLayoutBoundSubview<T>> = ArrayList()
    data.subscribeBy { value ->
        //Fix view count
        val excessViews = existingViews.size - value.size
        if(excessViews > 0){
            //remove views
            for(iter in 1 .. excessViews) {
                val old = existingViews.removeAt(existingViews.lastIndex)
                this.removeView(old.view)
            }
        } else if(existingViews.size < value.size) {
            //add views
            for(iter in 1 .. (-excessViews)) {
                val prop = StandardObservableProperty(defaultValue)
                val view = makeView(prop)
                this.addView(view, this.params(gravity = AlignPair.center))
                existingViews.add(LinearLayoutBoundSubview(view, prop))
            }
        }

        //Update views
        for(index in value.indices){
            existingViews[index].property.value = value[index]
        }
    }.until(this.removed)
}

