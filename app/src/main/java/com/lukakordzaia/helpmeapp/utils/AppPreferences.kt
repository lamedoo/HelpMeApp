package com.lukakordzaia.helpmeapp.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "HelperApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USER_TOKEN = "user_token" to ""
    private val DARK_MODE = "DarkModeState" to false

    private val HELPER_NAME = "helper_name" to ""
    private val HELPER_ID = "helper_id" to ""


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

    var helper_name: String
        get() = preferences.getString(HELPER_NAME.first, HELPER_NAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(HELPER_NAME.first, value)
        }

    var helper_id: String
        get() = preferences.getString(HELPER_ID.first, HELPER_ID.second) ?: ""
        set(value) = preferences.edit {
            it.putString(HELPER_ID.first, value)
        }
}