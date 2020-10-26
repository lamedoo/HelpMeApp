package com.lukakordzaia.helpmeapp.ui.helpers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.fragment_helpers.*

class HelpersFragment : Fragment(R.layout.fragment_helpers) {
    private lateinit var viewModel: HelpersViewModel
    private lateinit var adapter: HelpersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpersViewModel::class.java)
        adapter = HelpersAdapter(requireContext())
        rv_helpers.adapter = adapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helpers.setVisibleOrGone(it)
        })

        viewModel.helpersList.observe(viewLifecycleOwner, Observer {
            adapter.setHelpersList(it)
        })

        viewModel.getAllHelpers()
    }
}