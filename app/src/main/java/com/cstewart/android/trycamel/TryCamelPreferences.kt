package com.cstewart.android.trycamel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TryCamelPreferences(private val context: Context) {

    var url: String
        get() {
            val localeUrls = context.resources.getStringArray(R.array.locale_url)
            return sharedPreferences.getString(PREF_URL, localeUrls[0]) ?: localeUrls[0]
        }
        set(value) {
            sharedPreferences.edit { putString(PREF_URL, value) }
        }

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(SHARED_PREFS_TRY_CAMEL, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREFS_TRY_CAMEL = "trycamel"
        private const val PREF_URL = "search_url"
    }
}
