package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.fragment_helper_details.*

class HelperDetailsFragment : Fragment() {
    fun newInstance(helperName: String?): HelperDetailsFragment? {
        val frag = HelperDetailsFragment()
        val args = Bundle()
        args.putString("helperName", helperName)
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_helper_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val helperName = bundle?.getString("helperName")

        tv_helper_detail_name.text = helperName
    }
}