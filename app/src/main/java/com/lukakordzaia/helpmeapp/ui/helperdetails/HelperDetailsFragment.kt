package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_helper_details.*

class HelperDetailsFragment : Fragment(R.layout.fragment_helper_details) {
    private lateinit var viewModel: HelperDetailsViewModel
    private val args: HelperDetailsFragmentArgs by navArgs()

//    companion object {
//        fun newInstance(helperName: String?) =
//            HelperDetailsFragment().applyBundle {
//                putString("helperName", helperName)
//            }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelperDetailsViewModel::class.java)
        val helperId = args.helperId

        viewModel.getSingleHelper(helperId)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helper_detail.setVisibleOrGone(it)
            if (!it) {
                helper_details_top.setVisibleOrGone(true)
                helper_details_reviews.setVisibleOrGone(true)
                btn_helper_details_order.setVisibleOrGone(true)
            }
        })

        viewModel.helperData.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.avatar).into(iv_helper_details_avatar)
            tv_helper_detail_name.text = it.name
            tv_helper_details_bio.text = it.bio
            tv_helper_details_price.text = it.price.toString()
            tv_helper_details_rating.text = it.rating.toString()
            tv_helper_details_jobCount.text = it.jobs_done.toString()
        })

        btn_helper_details_order.setOnClickListener {
            viewModel.onOrderPressed(helperId, viewModel.helperData.value!!.name)
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }
}