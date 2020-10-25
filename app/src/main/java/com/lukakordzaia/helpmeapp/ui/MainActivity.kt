package com.lukakordzaia.helpmeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersFragment

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val helpersFragment = HelpersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().add(R.id.fragment_container, helpersFragment)
            .show(helpersFragment)
            .addToBackStack(null)
            .commit()
    }
}