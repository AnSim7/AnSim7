package com.example.myapplication.core

import android.content.Context
import android.content.SharedPreferences

class UserSession(context: Context) {
    private var sharedPrefs: SharedPreferences = context.getSharedPreferences(NEED_ONBOARDING, Context.MODE_PRIVATE)

    private var sharedPrefsEditor: SharedPreferences.Editor = sharedPrefs.edit()

    private fun getBool(key: String) = sharedPrefs.getBoolean(key, true)

    //isAbonent и idCity будут браться из API, пока пусть будет так
    var isAbonent: Boolean = true
        get() = field

    var idCity: Int = 74
        get() = field

    val inNeedShowOnboarding: Boolean
        get() = getBool(ONBOARDING)

    fun sync(bool: Boolean) {
        sharedPrefsEditor
            .putBoolean(ONBOARDING, bool)
            .apply()
    }

    fun clean() {
        sharedPrefsEditor
            .clear()
            .apply()
    }

    companion object {
        private val TAG = UserSession::class.java

        private const val NEED_ONBOARDING = "NEED_ONBOARDING"
        private const val ONBOARDING = "ONBOARDING"
    }

}