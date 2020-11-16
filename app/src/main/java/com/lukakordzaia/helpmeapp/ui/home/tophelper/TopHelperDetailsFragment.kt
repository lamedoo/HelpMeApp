package com.lukakordzaia.helpmeapp.ui.home.tophelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_top_helper_details.*


class TopHelperDetailsFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: TopHelperDetailsViewModel
    private val args: TopHelperDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_helper_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopHelperDetailsViewModel::class.java)
        val helperId = args.helperId
        viewModel.getTopHelper(helperId)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_top_helper_detail.setVisibleOrGone(it)
            if (!it) {
                top_helper_details_top.setVisibleOrGone(true)
                btn_top_helper_details_order.setVisibleOrGone(true)
            }
        })

        viewModel.helperData.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.avatar).into(iv_top_helper_details_avatar)
            tv_top_helper_detail_name.text = it.name
            tv_top_helper_details_bio.text = it.bio
            tv_top_helper_details_price.text = it.price.toString()
            tv_top_helper_details_rating.text = it.rating.toString()
            tv_top_helper_details_jobCount.text = it.jobs_done.toString()
        })

        btn_top_helper_details_order.setOnClickListener {
            viewModel.onOrderPressed(helperId, viewModel.helperData.value!!.name)
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }

    }
}