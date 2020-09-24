package com.lightningkite.butterfly.views

import com.lightningkite.butterfly.observables.ObservableStack

interface EntryPoint: HasBackAction {
    fun handleDeepLink(schema: String, host: String, path: String, params: Map<String, String>){
        println("Empty handler; $schema://$host/$path/$params")
    }
    val mainStack: ObservableStack<ViewGenerator>? get() = null
}
