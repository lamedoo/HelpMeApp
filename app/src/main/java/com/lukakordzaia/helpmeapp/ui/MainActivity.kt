package com.lukakordzaia.helpmeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.MediaController
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MotionEventCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersFragment
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import com.lukakordzaia.helpmeapp.utils.setGone
import com.lukakordzaia.helpmeapp.utils.setVisible
import com.lukakordzaia.helpmeapp.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
//    private lateinit var navController: NavController
    private lateinit var currentNavController: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        AppPreferences.init(this)
        if (!AppPreferences.dark_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appToolbar: MaterialToolbar = findViewById(R.id.app_main_toolbar)
        setSupportActionBar(appToolbar)

        setBottomNav()

    }

//    private fun setBottomNav() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fr_nav_host) as NavHostFragment
//        navController  = navHostFragment.navController
//        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
//        NavigationUI.setupWithNavController(nav_main_bottom, navController)
//
//        if (AppPreferences.user_token.isNotEmpty()) {
//            navGraph.startDestination = R.id.homeFragment
//        } else {
//            navGraph.startDestination = R.id.loginFragment
//        }
//        navController.graph = navGraph
//
//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf(
//                R.id.loginFragment,
//                R.id.homeFragment,
//                R.id.ordersFragment,
//                R.id.userProfileFragment
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//    }

    private fun setBottomNav() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_main_bottom)
        val navGraphIds = listOf(R.navigation.bottom_tab_home, R.navigation.bottom_tab_orders, R.navigation.bottom_tab_profile)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.loginFragment,
                R.id.homeFragment,
                R.id.ordersFragment,
                R.id.userProfileFragment,
            )
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fr_nav_host,
            intent = intent
        )
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController, appBarConfiguration)
        })
        currentNavController = controller
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.value?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    fun showBottomNavigation() {
        nav_main_bottom.setVisible()
    }

    fun hideBottomNavigation() {
        nav_main_bottom.setGone()
    }
}