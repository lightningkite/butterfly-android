package com.lightningkite.butterfly.android

import android.app.Application
import androidx.preference.PreferenceManager
import com.lightningkite.butterfly.ApplicationAccess
import com.lightningkite.butterfly.Preferences
import com.lightningkite.butterfly.net.HttpClient

open class KhrysalisApplication: Application() {
    companion object {
        fun setup(application: Application){
            HttpClient.appContext = application
            Preferences.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
            ApplicationAccess.applicationIsActiveStartup(application)
        }
    }

    override fun onCreate() {
        super.onCreate()
        setup(this)
    }
}