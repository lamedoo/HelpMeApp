package com.lukakordzaia.helpmeapp.ui.orderhelper.ordercheck

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.navController
import com.ncorti.slidetoact.SlideToActView
import com.ncorti.slidetoact.SlideToActView.OnSlideCompleteListener
import kotlinx.android.synthetic.main.fragment_order_check_final.*

class OrderCheckFinalFragment : Fragment(R.layout.fragment_order_check_final) {
    private lateinit var viewModel: OrderCheckFinalViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderCheckFinalViewModel::class.java)

        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            tv_check_final_date.text = it.orderDate
            tv_check_final_address.text = it.orderAddress
            tv_check_final_helper.text = it.orderHelper
            tv_check_final_kitchen.text = it.orderServices.kitchen.toString()
            tv_check_final_living.text = it.orderServices.living.toString()
            tv_check_final_studio.text = it.orderServices.studio.toString()
            tv_check_final_bedroom.text = it.orderServices.bedroom.toString()
            tv_check_final_bathroom.text = it.orderServices.bathroom.toString()
            tv_check_final_office.text = it.orderServices.office.toString()
        })

            check_final_confirm.onSlideCompleteListener = object : OnSlideCompleteListener {
                override fun onSlideComplete(view: SlideToActView) {
                    viewModel.createNewOrder()
                    viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                        context.createToast(it)
                    })
                    viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                        navController(it)
                    })
                }

            }
    }
}