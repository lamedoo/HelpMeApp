package com.lukakordzaia.helpmeapp.ui.userprofile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*

class UserProfileEditFragment : Fragment(R.layout.fragment_user_profile_edit) {
    private lateinit var viewModel: UserProfileEditViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileEditViewModel::class.java)

        viewModel.userDataList.observe(viewLifecycleOwner, Observer {
            (tv_user_profile_edit_name as TextView).text = it.name
            (tv_user_profile_edit_lastName as TextView).text = it.lastName
            (tv_user_profile_edit_email as TextView).text = it.email
            (tv_user_profile_edit_number as TextView).text = it.phone
        })
    }
}