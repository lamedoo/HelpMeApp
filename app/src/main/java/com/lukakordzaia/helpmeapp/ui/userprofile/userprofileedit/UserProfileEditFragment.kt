package com.lukakordzaia.helpmeapp.ui.userprofile.userprofileedit

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*


class UserProfileEditFragment : Fragment(R.layout.fragment_user_profile_edit) {
    private lateinit var viewModel: UserProfileEditViewModel
    private var filePath: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 71
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileEditViewModel::class.java)

        viewModel.userDataList.observe(viewLifecycleOwner, Observer {
            if (!it.avatar.equals("null")) {
                Picasso.get().load(it.avatar).into(iv_user_profile_edit_avatar)
            }
            (tv_user_profile_edit_name as TextView).text = it.name
            (tv_user_profile_edit_lastName as TextView).text = it.lastName
            (tv_user_profile_edit_email as TextView).text = it.email
            (tv_user_profile_edit_number as TextView).text = it.phone
            Log.d("nametext", "This is last name${it.lastName}")
        })

        btn_user_profile_edit_avatar_choose.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
                filePath = data.data
                Picasso.get().load(filePath).into(iv_user_profile_edit_avatar)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.user_edit_save) {
            val name = tv_user_profile_edit_name.text.toString()
            val lastName = tv_user_profile_edit_lastName.text.toString()
            val email = tv_user_profile_edit_email.text.toString()
            val number = tv_user_profile_edit_number.text.toString()

            viewModel.updateUserData(requireContext(), name, lastName, email, number, filePath)
            viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                context.createToast(it)
            })
        }

        return super.onOptionsItemSelected(item)
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