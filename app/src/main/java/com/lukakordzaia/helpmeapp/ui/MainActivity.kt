package com.lukakordzaia.helpmeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersFragment
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val helpersFragment = HelpersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, helpersFragment).show(helpersFragment)
        }.commit()
    }
}