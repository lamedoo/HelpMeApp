package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.navController
import kotlinx.android.synthetic.main.fragment_order_choose_details.*
import kotlinx.android.synthetic.main.order_fragments_progress_dots.*


class OrderChooseDetailsFragment : Fragment(R.layout.fragment_order_choose_details) {
    private lateinit var viewModel: OrderChooseDetailsViewModel
    private lateinit var adapter: OrderChooseDetailsAdapter
    private val args: OrderChooseDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderChooseDetailsViewModel::class.java)

        viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
            navController(it)
        })

        viewModel.getUserAddresses()
        adapter = OrderChooseDetailsAdapter(requireContext()) {
            viewModel.setChosenAddress(it)
        }
        rv_choose_address.adapter = adapter

        viewModel.chosenAddress.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.chosenAddress(it.id)
                btn_order_helper_details_next.setOnClickListener { _ ->
                    viewModel.onNextButtonPressed(args.cleaningOption, it.address)
                }
            } else {
                btn_order_helper_details_next.setOnClickListener {
                    context.createToast("გთხოვთ აირჩიოთ მისამართი")
                }
            }
        })

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            adapter.setAddressList(it)
        })

        fab_user_address_add.setOnClickListener {
            viewModel.onAddAddressPressed()
        }

        progress_dot_1.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_700)
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