package com.lukakordzaia.helpmeapp.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
            navController(it)
        })

        viewModel.onTokenExists()

        btn_login.setOnClickListener {
            checkLoginInfo()
            val email = tv_login_email.text.toString()
            val password = tv_login_password.text.toString()

            tv_login_password.clearFocus()

            viewModel.userLogin(FirebaseAuth.getInstance(), email, password)
        }

        viewModel.loginError.observe(viewLifecycleOwner, Observer {
            login_title.text = it
            login_title.setTextColor(Color.RED)
        })

        btn_goto_register.setOnClickListener {
            viewModel.onRegisterPressed()
        }
    }

    private fun checkLoginInfo() {
        if (tv_login_email.text.toString().isEmpty()) {
            tv_login_email.error = "შეიყვანეთ ელ-ფოსტა"
            tv_login_email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_login_email.text.toString()).matches()) {
            tv_login_email.error = "შეიყვანეთ ვალიდური ელ-ფოსტა"
            tv_login_email.requestFocus()
            return
        }

        if (tv_login_password.text.toString().isEmpty()) {
            tv_login_password.error = "შეიყვანეთ პაროლი"
            tv_login_password.requestFocus()
            return
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

}