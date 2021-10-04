@file:Suppress("DEPRECATION")

package com.lightningkite.butterfly.observables.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.exoplayer2.ui.PlayerView
import com.lightningkite.butterfly.Video
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.butterfly.observables.MutableObservableProperty
import com.lightningkite.butterfly.observables.ObservableProperty
import com.lightningkite.butterfly.observables.StandardObservableProperty
import com.lightningkite.butterfly.views.ColorResource
import com.lightningkite.butterfly.views.StringResource
import com.lightningkite.rxkotlinproperty.*
import com.lightningkite.rxkotlinproperty.android.RVTypeHandler
import com.lightningkite.rxkotlinproperty.android.removed
import com.lightningkite.rxkotlinproperty.android.resources.Image
import com.lightningkite.rxkotlinproperty.android.resources.VideoPlayer
import com.lightningkite.rxkotlinproperty.android.setRemovedCondition
import com.lightningkite.rxkotlinproperty.viewgenerators.ActivityAccess
import com.lightningkite.rxkotlinproperty.viewgenerators.SwapView
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewGenerator

@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindString", "com.lightningkite.rxkotlinproperty.android.bindString"))
fun TextView.bindString(observable:ObservableProperty<String>) = this.new_bindString(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindExists", "com.lightningkite.rxkotlinproperty.android.bindExists"))
fun View.bindExists(observable:ObservableProperty<Boolean>) = this.new_bindExists(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindVisible", "com.lightningkite.rxkotlinproperty.android.bindVisible"))
fun View.bindVisible(observable:ObservableProperty<Boolean>) = this.new_bindVisible(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun <T> RecyclerView.bind(data: Property<List<T>>, defaultValue: T, makeView: (Property<T>) -> View) = this.new_bind(data, defaultValue, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindActive", "com.lightningkite.rxkotlinproperty.android.bindActive"))
fun Button.bindActive(observable: ObservableProperty<Boolean>, activeBackground: Drawable, inactiveBackground: Drawable) = this.new_bindActive(observable, activeBackground, inactiveBackground)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindActive", "com.lightningkite.rxkotlinproperty.android.bindActive"))
fun Button.bindActive(observable: ObservableProperty<Boolean>, activeColorResource: ColorResource? = null, inactiveColorResource: ColorResource? = null) = this.new_bindActive(observable, activeColorResource, inactiveColorResource)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun RatingBar.bind(stars: Int, observable: MutableObservableProperty<Int>) = this.new_bind(stars, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun RatingBar.bind(stars: Int, observable: ObservableProperty<Int>) = this.new_bind(stars, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun RatingBar.bindFloat(stars: Int, observable: ObservableProperty<Float>) = this.new_bindFloat(stars, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun RatingBar.bindFloat(stars: Int, observable: MutableObservableProperty<Float>) = this.new_bindFloat(stars, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindSelect", "com.lightningkite.rxkotlinproperty.android.bindSelect"))
fun <T> CompoundButton.bindSelect(value: T, observable: MutableObservableProperty<T>) = this.new_bindSelect(value, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindSelectNullable", "com.lightningkite.rxkotlinproperty.android.bindSelectNullable"))
fun <T> CompoundButton.bindSelectNullable(value: T, observable: MutableObservableProperty<T?>) = this.new_bindSelectNullable(value, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindSelectInvert", "com.lightningkite.rxkotlinproperty.android.bindSelectInvert"))
fun <T> CompoundButton.bindSelectInvert(value: T, observable: MutableObservableProperty<T?>) = this.new_bindSelectInvert(value, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun CompoundButton.bind(observable: MutableObservableProperty<Boolean>) = this.new_bind(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindRefresh", "com.lightningkite.rxkotlinproperty.android.bindRefresh"))
fun RecyclerView.bindRefresh(loading: ObservableProperty<Boolean>, refresh: () -> Unit) = this.new_bindRefresh(loading, refresh)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindMulti", "com.lightningkite.rxkotlinproperty.android.bindMulti"))
fun RecyclerView.bindMulti(data: ObservableProperty<List<Any>>, typeHandlerSetup: (RVTypeHandler)->Unit) = this.new_bindMulti(data, typeHandlerSetup)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindMulti", "com.lightningkite.rxkotlinproperty.android.bindMulti"))
fun <T> RecyclerView.bindMulti(data: ObservableProperty<List<T>>, defaultValue: T, determineType: (T)->Int, makeView: (Int, ObservableProperty<T>) -> View) = this.new_bindMulti(data, defaultValue, determineType, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun <T> LinearLayout.bind(data: ObservableProperty<List<T>>, defaultValue: T, makeView: (ObservableProperty<T>) -> View) = this.new_bind(data, defaultValue, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindHorizontal", "com.lightningkite.rxkotlinproperty.android.bindHorizontal"))
fun <T> LinearLayout.bindHorizontal(data: ObservableProperty<List<T>>, defaultValue: T, makeView: (ObservableProperty<T>) -> View) = this.new_bindHorizontal(data, defaultValue, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindOnOffString", "com.lightningkite.rxkotlinproperty.android.bindOnOffString"))
fun ToggleButton.bindOnOffString(observable: ObservableProperty<String>) = this.new_bindOnOffString(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindStack", "com.lightningkite.rxkotlinproperty.android.bindStack"))
fun <T: ViewGenerator> SwapView.bindStack(dependency: ActivityAccess, obs: PropertyStack<T>) = this.new_bindStack(dependency, obs)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindStringRes", "com.lightningkite.rxkotlinproperty.android.bindStringRes"))
fun TextView.bindStringRes(observable: ObservableProperty<StringResource?>) = this.new_bindStringRes(observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun <T> Spinner.bind(options: Property<List<T>>, selected: MutableProperty<T>, toString: (T) -> String = { it.toString() }) = this.new_bind(options, selected, toString)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindString", "com.lightningkite.rxkotlinproperty.android.bindString"))
fun <T> Spinner.bindString(options: Property<List<T>>, selected: MutableProperty<T>, toString: (T) -> Property<String>) = this.new_bindString(options, selected, toString)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindLoading", "com.lightningkite.rxkotlinproperty.android.bindLoading"))
fun ViewFlipper.bindLoading(loading: ObservableProperty<Boolean>, color: ColorResource? = null) = this.new_bindLoading(loading, color)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun <T> ViewPager.bind(items: List<T>, showIndex: MutableObservableProperty<Int> = StandardObservableProperty(0), makeView: (T)->View) = this.new_bind(items, showIndex, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun <T> ViewPager.bind(data: ObservableProperty<List<T>>, defaultValue: T, showIndex: MutableObservableProperty<Int> = StandardObservableProperty(0), makeView: (ObservableProperty<T>)->View) = this.new_bind(data, defaultValue, showIndex, makeView)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("serves", "com.lightningkite.rxkotlinproperty.serves"))
fun <T> MutableObservableProperty<T>.serves(until: DisposeCondition, other: MutableObservableProperty<T>) = this.new_serves(until, other)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.bind"))
fun SeekBar.bind(start: Int, endInclusive: Int, observable: MutableObservableProperty<Int>) = this.new_bind(start, endInclusive, observable)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindString", "com.lightningkite.rxkotlinproperty.android.bindString"))
fun EditText.bindString(property: MutableObservableProperty<String>) = this.new_bindString(property)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindInteger", "com.lightningkite.rxkotlinproperty.android.bindInteger"))
fun EditText.bindInteger(property: MutableObservableProperty<Int>) = this.new_bindInteger(property)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindDouble", "com.lightningkite.rxkotlinproperty.android.bindDouble"))
fun EditText.bindDouble(property: MutableObservableProperty<Double>) = this.new_bindDouble(property)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindIntegerNullable", "com.lightningkite.rxkotlinproperty.android.bindIntegerNullable"))
fun EditText.bindIntegerNullable(property: MutableObservableProperty<Int?>) = this.new_bindIntegerNullable(property)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindDoubleNullable", "com.lightningkite.rxkotlinproperty.android.bindDoubleNullable"))
fun EditText.bindDoubleNullable(property: MutableObservableProperty<Double?>) = this.new_bindDoubleNullable(property)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindList", "com.lightningkite.rxkotlinproperty.android.bindList"))
fun <T> AutoCompleteTextView.bindList(options: ObservableProperty<List<T>>, toString: (T) -> String, onItemSelected: (T) -> Unit) = this.new_bindList(options, toString, onItemSelected)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindImage", "com.lightningkite.rxkotlinproperty.android.resources.bindImage"))
fun ImageView.bindImage(image: ObservableProperty<Image?>) = this.new_bindImage(image)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindVideoThumbnail", "com.lightningkite.rxkotlinproperty.android.resources.bindVideoThumbnail"))
fun ImageView.bindVideoThumbnail(video: ObservableProperty<Video?>) = this.new_bindVideoThumbnail(video)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.resources.bind"))
fun VideoPlayer.bind(video: ObservableProperty<Video?>) = this.new_bind(video)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bind", "com.lightningkite.rxkotlinproperty.android.resources.bind"))
fun PlayerView.bind(video: ObservableProperty<Video?>) = this.new_bind(video)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindAndStart", "com.lightningkite.rxkotlinproperty.android.resources.bindAndStart"))
fun VideoPlayer.bindAndStart(video: ObservableProperty<Video?>) = this.new_bindAndStart(video)
@Deprecated("Use directly from RxKotlin Properties", replaceWith = ReplaceWith("bindAndStart", "com.lightningkite.rxkotlinproperty.android.resources.bindAndStart"))
fun PlayerView.bindAndStart(video: ObservableProperty<Video?>) = this.new_bindAndStart(video)




@JvmName("bindComplex")
@Deprecated("Doesn't translate to web. Use the versions that have a toString rather than makeView.")
fun <T> Spinner.bind(
    options: Property<List<T>>,
    selected: MutableProperty<T>,
    makeView: (Property<T>) -> View
) {
    adapter = object : BaseAdapter() {
        init {
            options.subscribeBy { _ ->
                this.notifyDataSetChanged()
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: run {
                val event = StandardProperty<T>(
                    options.value.getOrNull(position) ?: selected.value
                )
                val subview = makeView(event)
                subview.setRemovedCondition(this@bind.removed)
                subview.tag = event
                return subview
            }
            (view.tag as? StandardProperty<T>)?.let {
                it.value = options.value.getOrNull(position) ?: selected.value
            } ?: throw IllegalStateException()
            return view
        }

        override fun getItem(position: Int): Any? = options.value.getOrNull(position)
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getCount(): Int = options.value.size
    }
    selected.subscribeBy { it ->
        val index = options.value.indexOf(it)
        if (index != -1 && index != selectedItemPosition) {
            setSelection(index)
        }
    }.until(this.removed)
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val newValue = options.value.getOrNull(position) ?: return
            if (selected.value != newValue) {
                selected.value = newValue
            }
        }
    }
}
