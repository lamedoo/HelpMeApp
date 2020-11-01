package com.lukakordzaia.helpmeapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object AppPreferences {
    private const val NAME = "HelperApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USER_TOKEN = Pair("user_token", "")
    private val DARK_MODE = Pair("DarkModeState", false)


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var dark_mode: Boolean
        get() = preferences.getBoolean(DARK_MODE.first, DARK_MODE.second)
        set(value) = preferences.edit {
            it.putBoolean(DARK_MODE.first, value)
        }

    var user_token: String
        get() = preferences.getString(USER_TOKEN.first, USER_TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USER_TOKEN.first, value)
        }

}