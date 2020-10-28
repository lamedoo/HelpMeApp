package com.lukakordzaia.helpmeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersFragment
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersViewModel
import com.lukakordzaia.helpmeapp.utils.setGone
import com.lukakordzaia.helpmeapp.utils.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appToolbar = findViewById(R.id.app_main_toolbar)
        setSupportActionBar(appToolbar)

        setupViews()
    }

    private fun setupViews() {
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fr_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(nav_main_bottom, navController)

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.loginFragment,
                R.id.homeFragment,
                R.id.helpersFragment,
                R.id.ordersFragment,
                R.id.userProfileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun showBottomNavigation() {
        nav_main_bottom.setVisible()
    }

    fun hideBottomNavigation() {
        nav_main_bottom.setGone()
    }
}