package com.lukakordzaia.helpmeapp.ui.settings

import android.content.Context
import android.content.Intent
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences

class SettingsViewModel : BaseViewModel() {

    fun switchIsChecked(context: Context) {
        AppPreferences.dark_mode = true
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun switchIsNotChecked(context: Context) {
        AppPreferences.dark_mode = false
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}