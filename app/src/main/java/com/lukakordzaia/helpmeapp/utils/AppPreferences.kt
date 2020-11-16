package com.lukakordzaia.helpmeapp.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "HelperApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USER_TOKEN = "user_token" to ""
    private val DARK_MODE = "DarkModeState" to false


    private val ORDER_CLEANING_OPTION = "order_cleaning_option" to ""
    private val ORDER_DATE = "order_date" to ""
    private val ORDER_ADDRESS = "order_address" to ""
    private val SERVICE_KITCHEN = "service_kitchen" to 0
    private val SERVICE_LIVING = "service_living" to 0
    private val SERVICE_STUDIO = "service_studio" to 0
    private val SERVICE_BEDROOM = "service_bedroom" to 0
    private val SERVICE_BATHROOM = "service_bathroom" to 0
    private val SERVICE_OFFICE = "service_office" to 0
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

    var order_cleaning_option: String
        get() = preferences.getString(ORDER_CLEANING_OPTION.first, ORDER_CLEANING_OPTION.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ORDER_CLEANING_OPTION.first, value)
        }

    var order_date: String
        get() = preferences.getString(ORDER_DATE.first, ORDER_DATE.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ORDER_DATE.first, value)
        }

    var order_address: String
        get() = preferences.getString(ORDER_ADDRESS.first, ORDER_ADDRESS.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ORDER_ADDRESS.first, value)
        }

    var service_kitchen: Int
        get() = preferences.getInt(SERVICE_KITCHEN.first, SERVICE_KITCHEN.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_KITCHEN.first, value)
        }

    var service_living: Int
        get() = preferences.getInt(SERVICE_LIVING.first, SERVICE_LIVING.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_LIVING.first, value)
        }

    var service_studio: Int
        get() = preferences.getInt(SERVICE_STUDIO.first, SERVICE_STUDIO.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_STUDIO.first, value)
        }

    var service_bedroom: Int
        get() = preferences.getInt(SERVICE_BEDROOM.first, SERVICE_BEDROOM.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_BEDROOM.first, value)
        }

    var service_bathroom: Int
        get() = preferences.getInt(SERVICE_BATHROOM.first, SERVICE_BATHROOM.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_BATHROOM.first, value)
        }

    var service_office: Int
        get() = preferences.getInt(SERVICE_OFFICE.first, SERVICE_OFFICE.second)
        set(value) = preferences.edit {
            it.putInt(SERVICE_OFFICE.first, value)
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