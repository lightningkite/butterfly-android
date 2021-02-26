package com.lightningkite.butterfly.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.lightningkite.butterfly.*
import com.lightningkite.butterfly.net.HttpClient

open class ButterflyApplication: Application() {
    companion object {
        fun setup(application: Application){
            HttpClient.appContext = application
            Log = AndroidLog
            Preferences.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
            SecurePreferences.sharedPreferences = application.getSharedPreferences("secure", Context.MODE_PRIVATE)
            ApplicationAccess.applicationIsActiveStartup(application)
        }
    }

    override fun onCreate() {
        super.onCreate()
        setup(this)
    }
}