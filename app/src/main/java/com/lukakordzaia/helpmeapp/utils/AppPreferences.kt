package com.lukakordzaia.helpmeapp.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "HelperApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USER_TOKEN = Pair("user_token", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var user_token: String
        get() = preferences.getString(USER_TOKEN.first, USER_TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USER_TOKEN.first, value)
        }

}