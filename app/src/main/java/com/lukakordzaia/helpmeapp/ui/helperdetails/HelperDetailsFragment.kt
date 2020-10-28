package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.applyBundle
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_helper_details.*

class HelperDetailsFragment : Fragment(R.layout.fragment_helper_details) {
    private lateinit var viewModel: HelperDetailsViewModel

//    companion object {
//        fun newInstance(helperName: String?) =
//            HelperDetailsFragment().applyBundle {
//                putString("helperName", helperName)
//            }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelperDetailsViewModel::class.java)
        val helperId = arguments?.getInt("helperId")

        viewModel.getSingleHelper(helperId!!)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helper_detail.setVisibleOrGone(it)
        })

        viewModel.helperName.observe(viewLifecycleOwner, Observer {
            tv_helper_detail_name.text = it
        })
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