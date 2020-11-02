package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.applyBundle
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import com.squareup.picasso.Picasso
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

        viewModel.showContent.observe(viewLifecycleOwner, Observer {
            helper_details_top.setVisibleOrGone(it)
            helper_details_reviews.setVisibleOrGone(it)
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helper_detail.setVisibleOrGone(it)
        })

        viewModel.helperAvatar.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it).into(iv_helper_details_avatar)
        })

        viewModel.helperName.observe(viewLifecycleOwner, Observer {
            tv_helper_detail_name.text = it
        })

        viewModel.helperBio.observe(viewLifecycleOwner, Observer {
            tv_helper_details_bio.text = it
        })

        viewModel.helperPrice.observe(viewLifecycleOwner, Observer {
            tv_helper_details_price.text = it.toString()
        })

        viewModel.helperRating.observe(viewLifecycleOwner, Observer {
            tv_helper_details_rating.text = it.toString()
        })

        viewModel.helperJobsDone.observe(viewLifecycleOwner, Observer {
            tv_helper_details_jobCount.text = it.toString()
        })

        btn_helper_details_order.setOnClickListener {
            Toast.makeText(context, "Thank you for ordering", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }
}