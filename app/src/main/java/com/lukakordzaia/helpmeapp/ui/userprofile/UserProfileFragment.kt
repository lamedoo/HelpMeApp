package com.lukakordzaia.helpmeapp.ui.userprofile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
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

        viewModel.showContent.observe(viewLifecycleOwner, Observer {
            user_profile_top_container.setVisibleOrGone(it)
            user_profile_bottom_container.setVisibleOrGone(it)
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_user_profile.setVisibleOrGone(it)
        })


        viewModel.userDataList.observe(viewLifecycleOwner, Observer {
            if (!it.avatar.equals("null")) {
                Picasso.get().load(it.avatar).into(iv_user_profile_avatar)
            }
            Log.d(TAG, "${it.avatar}")
            tv_user_profile_name.text = "${it.name}" + "${it.lastName}"
            tv_user_profile_email.text = it.email
            tv_user_profile_number.text = it.phone
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