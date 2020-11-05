package com.lukakordzaia.helpmeapp.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.setGone
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

//        viewModel.userAvatar.observe(viewLifecycleOwner, Observer {
//            Picasso.get().load(it).into(iv_user_profile_avatar)
//        })

        viewModel.showContent.observe(viewLifecycleOwner, Observer {
            user_profile_top_container.setVisibleOrGone(it)
            user_profile_bottom_container.setVisibleOrGone(it)
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_user_profile.setVisibleOrGone(it)
        })

        viewModel.userFullName.observe(viewLifecycleOwner, Observer {
            tv_user_profile_name.text = it
        })

        viewModel.userEmail.observe(viewLifecycleOwner, Observer {
            tv_user_profile_email.text = it
        })

        viewModel.userNumber.observe(viewLifecycleOwner, Observer {
            tv_user_profile_number.text = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.user_edit) {
            findNavController().navigate(R.id.action_userProfileFragment_to_userProfileEditFragment)
            return true
        } else if (id == R.id.user_logout) {
            viewModel.removeSaveToken()
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}