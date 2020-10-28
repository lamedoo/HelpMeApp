package com.lukakordzaia.helpmeapp.ui.helpers

import android.app.Application
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_helpers.*

class HelpersFragment : Fragment(R.layout.fragment_helpers) {
    private lateinit var viewModel: HelpersViewModel
    private lateinit var adapter: HelpersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.helpers_search_menu, menu)
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
}