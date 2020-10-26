package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.helpers.applyBundle
import kotlinx.android.synthetic.main.fragment_helper_details.*

class HelperDetailsFragment : Fragment(R.layout.fragment_helper_details) {

    companion object {
        fun newInstance(helperName: String?) =
            HelperDetailsFragment().applyBundle {
                putString("helperName", helperName)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val helperName = bundle?.getString("helperName")

        tv_helper_detail_name.text = helperName
    }
}