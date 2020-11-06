package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_user_addresses.*

class UserAddressesFragment : Fragment(R.layout.fragment_user_addresses) {
    private lateinit var viewModel: UserAddressesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserAddressesViewModel::class.java)

        viewModel.addressExists.observe(viewLifecycleOwner, Observer {
            user_address_container.setVisibleOrGone(it)
            if (it) {
                Toast.makeText(context, "This is a toast", Toast.LENGTH_SHORT).show()
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