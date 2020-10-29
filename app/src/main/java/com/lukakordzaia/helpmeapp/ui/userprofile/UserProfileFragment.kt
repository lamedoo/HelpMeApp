package com.lukakordzaia.helpmeapp.ui.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private lateinit var viewModel: UserProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        viewModel.userAvatar.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it).into(iv_user_profile_avatar)
        })

        viewModel.userFullName.observe(viewLifecycleOwner, Observer {
            tv_user_profile_name.text = it
        })

        btn_user_profile_logout.setOnClickListener {
            viewModel.removeSaveToken()
            findNavController().navigate(R.id.action_userProfileFragment_to_loginFragment)
        }
    }
}