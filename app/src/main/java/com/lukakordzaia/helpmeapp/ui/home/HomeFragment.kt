package com.lukakordzaia.helpmeapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showBottomNavigation()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = HomeAdapter(requireContext()) { helperId ->
            viewModel.onHelpersPressed(helperId)

            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }
        rv_top_helpers.loadSkeleton(R.layout.rv_top_helpers_item)
        rv_top_helpers.adapter = adapter

        btn_main_chooseServices.setOnClickListener {
            val chooseCleaningOptionsFragment = ChooseCleaningOptionsFragment()
            chooseCleaningOptionsFragment.show(childFragmentManager, chooseCleaningOptionsFragment.tag)
        }

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (!it) {
                rv_top_helpers.hideSkeleton()
            }
        })

        viewModel.topHelpersList.observe(viewLifecycleOwner, Observer {
            adapter.setTopHelpersList(it)
        })

        viewModel.getTopHelpers()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home_settings) {
            viewModel.onSettingsPressed()
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).showBottomNavigation()
    }
}