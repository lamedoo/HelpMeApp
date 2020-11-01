package com.lukakordzaia.helpmeapp.ui.helpers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_helpers.*

class HelpersFragment : Fragment(R.layout.fragment_helpers) {
    private lateinit var viewModel: HelpersViewModel
    private lateinit var adapter: HelpersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpersViewModel::class.java)

        viewModel.getAllHelpers()
        adapter = HelpersAdapter(requireContext(), findNavController())
        rv_helpers.adapter = adapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helpers.setVisibleOrGone(it)
        })

        viewModel.helpersList.observe(viewLifecycleOwner, Observer {
            adapter.setHelpersList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.helpers_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.helpers_filter_byRating) {
            viewModel.getHelpersByRating()
            return true
        } else if (id == R.id.helpers_filter_byPrice) {
            viewModel.getHelpersByPrice()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }
}