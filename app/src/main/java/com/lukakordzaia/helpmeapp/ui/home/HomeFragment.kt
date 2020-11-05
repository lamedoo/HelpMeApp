package com.lukakordzaia.helpmeapp.ui.home

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.ui.helpers.HelpersAdapter
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
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
        adapter = HomeAdapter(requireContext()) {
            val bundle = bundleOf("helperId" to it)
            findNavController().navigate(R.id.action_homeFragment_to_helperDetailsFragment, bundle)
        }
        rv_top_helpers.adapter = adapter

        btn_main_chooseHelpers.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_helpersFragment)
        }

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_top_helpers.setVisibleOrGone(it)
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
        var id = item.itemId
        if (id == R.id.home_settings) {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).showBottomNavigation()
    }
}