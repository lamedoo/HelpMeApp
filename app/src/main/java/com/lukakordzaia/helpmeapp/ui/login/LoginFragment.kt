package com.lukakordzaia.helpmeapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        login_button.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()

            viewModel.postUserLogin(PostUser(email, password))
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })

        viewModel.loginError.observe(viewLifecycleOwner, Observer {
            login_title.text = it
            login_title.setTextColor(Color.RED)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

}