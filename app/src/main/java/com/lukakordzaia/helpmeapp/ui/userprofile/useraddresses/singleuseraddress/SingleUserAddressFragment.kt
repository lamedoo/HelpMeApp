package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses.singleuseraddress

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_single_user_address.*

class SingleUserAddressFragment : Fragment(R.layout.fragment_single_user_address) {
    private lateinit var viewModel: SingleUserAddressViewModel
    private val args: SingleUserAddressFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SingleUserAddressViewModel::class.java)
        val addressId = args.addressId

        viewModel.getSingleAddress(addressId)
        viewModel.addressInfo.observe(viewLifecycleOwner, Observer {
            tv_single_address_title.text = it.address
            if (it.details != "null") {
                (tv_single_address_details as TextView).text = it.details
            }
        })

        tv_single_address_details.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.addAddressDetails(addressId, v.text.toString())
                v.clearFocus()
                v.hideKeyboard()
                viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                    context.createToast(it)
                })
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}