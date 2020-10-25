package com.lukakordzaia.helpmeapp.ui.helpers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_helpers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelpersFragment : Fragment() {
    private lateinit var viewModel: HelpersViewModel
    private lateinit var adapter: HelpersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_helpers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpersViewModel::class.java)
        adapter = HelpersAdapter(requireContext())
        rv_helpers.adapter = adapter

        viewModel.getAllHelpers()


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it) {
                pb_helpers.visibility = View.VISIBLE
            } else {
                pb_helpers.visibility = View.GONE
            }
        })

        viewModel.helpersList.observe(viewLifecycleOwner, Observer {
            adapter.setHelpersList(it)
        })
    }
}