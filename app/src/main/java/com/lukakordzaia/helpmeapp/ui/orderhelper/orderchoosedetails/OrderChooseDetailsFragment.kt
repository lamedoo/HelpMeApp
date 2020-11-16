package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.navController
import kotlinx.android.synthetic.main.fragment_order_choose_details.*


class OrderChooseDetailsFragment : Fragment(R.layout.fragment_order_choose_details) {
    private lateinit var viewModel: OrderChooseDetailsViewModel
    private lateinit var adapter: OrderChooseDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderChooseDetailsViewModel::class.java)

        order_helper_date.minDate = (System.currentTimeMillis() + 48*60*60*1000)

        viewModel.getUserAddresses()
        adapter = OrderChooseDetailsAdapter(requireContext()) {
            viewModel.chosenAddressName(it)
        }
        rv_choose_address.adapter = adapter
        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            adapter.setAddressList(it)
        })

        viewModel.chooseAddress.observe(viewLifecycleOwner, Observer { address ->
            btn_order_helper_details_next.setOnClickListener {
                if (!address.isNullOrEmpty()) {
                    viewModel.saveChosenDateAddress(
                        "${order_helper_date.dayOfMonth}/${order_helper_date.month + 1}/${order_helper_date.year}",
                        "$address"
                    )
                    viewModel.onSliderCompleted()
                    viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                        navController(it)
                    })
                } else {
                    context.createToast("გთხოვთ აირჩიოთ მისამართი")
                }
            }
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