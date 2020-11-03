package com.lukakordzaia.helpmeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import com.lukakordzaia.helpmeapp.utils.setGone
import com.lukakordzaia.helpmeapp.utils.setVisible
import com.lukakordzaia.helpmeapp.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    private lateinit var navController: NavController
    private lateinit var currentNavController: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        AppPreferences.init(this)
        if (!AppPreferences.dark_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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