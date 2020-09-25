package com.lightningkite.butterfly

import android.content.SharedPreferences

/**
 * Y
 */
object SecurePreferences {

    lateinit var sharedPreferences: SharedPreferences

    inline fun <reified T> set(key: String, value: T) {
        sharedPreferences.edit().putString(key, value.toJsonString()).apply()
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    inline fun <reified T: IsCodable> get(key: String): T? {
        val raw = sharedPreferences.getString(key, null)
        val result = raw?.fromJsonString<T>()
        return result
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
