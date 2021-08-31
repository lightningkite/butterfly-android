@file:Suppress("DEPRECATION")

package com.lightningkite.butterfly.observables.binding

import android.view.View
import android.widget.TextView
import com.lightningkite.butterfly.deprecatedaliases.*
import com.lightningkite.butterfly.observables.ObservableProperty


@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindString", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindString"))
fun TextView.bindString(observable:ObservableProperty<String>){
    this.new_bindString(observable)
}

@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindExists", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindExists"))
fun View.bindExists(observable:ObservableProperty<Boolean>){
    this.new_bindExists(observable)
}


@Deprecated("Use directly from RxKotlin Properties. Import com.lightningkite.rxkotlinproperty.android.bindVisible", replaceWith = ReplaceWith("com.lightningkite.rxkotlinproperty.android.bindVisible"))
fun View.bindVisible(observable:ObservableProperty<Boolean>){
    this.new_bindVisible(observable)
}
