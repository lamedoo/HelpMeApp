package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.lukakordzaia.helpmeapp.utils.navController
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_user_addresses.*

class UserAddressesFragment : Fragment(R.layout.fragment_user_addresses) {
    private lateinit var viewModel: UserAddressesViewModel
    private lateinit var adapter: UserAddressesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(requireContext(), getString(R.string.google_maps_api_key))
        Places.createClient(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserAddressesViewModel::class.java)
        viewModel.getUserAddresses()
        adapter = UserAddressesAdapter(
            requireContext(),
            onDeleteClick = {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("ნამდვილად გსურთ მისამართის წაშლა?")
                    .setCancelable(false)
                    .setPositiveButton("დიახ") { _, _ ->
                        viewModel.deleteSingleAddress(it)
                        viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                            context.createToast(it)
                        })
                    }
                    .setNegativeButton("არა") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            },
            onItemClick = { addressId ->
                viewModel.onSingleAddressPressed(addressId)
                viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                    navController(it)
                })
            }
        )
        rv_addresses.adapter = adapter

        viewModel.addressList.observe(viewLifecycleOwner, Observer {
            adapter.setAddressList(it)
        })

        viewModel.noAddress.observe(viewLifecycleOwner, Observer {
            tv_users_address_none.setVisibleOrGone(it)
        })

        fab_user_address_add.setOnClickListener {
            viewModel.onAddNewAddressPressed()
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }
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