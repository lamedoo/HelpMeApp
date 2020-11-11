package com.lukakordzaia.helpmeapp.ui.userprofile.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
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

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_user_profile.setVisibleOrGone(it)
            if (it == false) {
                user_profile_top_container.setVisibleOrGone(true)
                user_profile_bottom_container.setVisibleOrGone(true)
            }
        })

        viewModel.getUserDataFromRoom(requireContext()).observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                if (!users.avatar.equals("null")) {
                    Picasso.get().load(users.avatar).into(iv_user_profile_avatar)
                }
                tv_user_profile_name.text = "${users.firstName} ${users.lastName}"
                tv_user_profile_email.text = users.email
                tv_user_profile_number.text = users.phone
            } else {
                viewModel.addUserChangeListener(requireContext())
                viewModel.userDataList.observe(viewLifecycleOwner, Observer {
                    if (!it.avatar.equals("null")) {
                        Picasso.get().load(it.avatar).into(iv_user_profile_avatar)
                    }
                    tv_user_profile_name.text = "${it.name} ${it.lastName}"
                    tv_user_profile_email.text = it.email
                    tv_user_profile_number.text = it.phone
                })
            }
        })

        user_profile_address_container.setOnClickListener {
            viewModel.onAddressesPressed()
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.user_edit) {
            viewModel.onUserEditPressed()
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it)
            })
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