package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchooseservices

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.navController
import kotlinx.android.synthetic.main.fragment_order_choose_services.*


class OrderChooseServicesFragment : Fragment(R.layout.fragment_order_choose_services) {
    private lateinit var viewModel: OrderChooseServicesViewModel
    private lateinit var adapter: OrderChooseServicesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderChooseServicesViewModel::class.java)
        adapter = OrderChooseServicesAdapter(requireContext()) { serviceName, amount ->
            viewModel.getServiceAmounts(serviceName, amount)
        }
        rv_choose_services.adapter = adapter

        viewModel.servicesList.observe(viewLifecycleOwner, Observer {
            adapter.setServicesList(it)
        })

        btn_order_helper_services_next.setOnClickListener {
            viewModel.checkServices()
            viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                context.createToast(it)
            })
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }
    }
}