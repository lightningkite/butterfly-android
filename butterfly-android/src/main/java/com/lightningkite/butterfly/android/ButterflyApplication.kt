package com.lightningkite.butterfly.android

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.net.HttpClient
import com.lightningkite.rxkotlinproperty.viewgenerators.AndroidLog
import com.lightningkite.rxkotlinproperty.viewgenerators.Log
import com.lightningkite.rxkotlinproperty.viewgenerators.ViewGeneratorApplication


@Deprecated("Use RxKotlin Property", replaceWith = ReplaceWith("ViewGeneratorApplication"))
open class ButterflyApplication: Application() {
    companion object {
        fun setup(application: Application){
            ViewGeneratorApplication.setup(application)
            HttpClient.appContext = application
            Log = AndroidLog
            Preferences.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
            SecurePreferences.sharedPreferences = application.getSharedPreferences("secure", Context.MODE_PRIVATE)
        }
    }

    override fun onCreate() {
        super.onCreate()
        setup(this)
    }
}