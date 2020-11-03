package com.lukakordzaia.helpmeapp.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var viewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        btn_register.setOnClickListener {
            signUpUser()
            val email = tv_register_email.text.toString()
            val name = tv_register_name.text.toString()
            val lastName = tv_register_lastName.text.toString()
            val number = tv_register_phone.text.toString()
            val password = tv_register_password.text.toString()

            tv_register_password.clearFocus()

            viewModel.userLogin(
                FirebaseAuth.getInstance(),
                email,
                password,
                name,
                lastName,
                number
            )
        }

        viewModel.registerSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "გთხოვთ გაიაროთ ავტორიზაცია", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "რეგისტრაცია ვერ ხერხდება", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun signUpUser() {
        if (tv_register_email.text.toString().isEmpty()) {
            tv_register_email.error = "შეიყვანეთ ელ-ფოსტა"
            tv_register_email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_register_email.text.toString()).matches()) {
            tv_register_email.error = "შეიყვანეთ ვალიდური ელ-ფოსტა"
            tv_register_email.requestFocus()
            return
        }

        if (tv_register_password.text.toString().isEmpty()) {
            tv_register_password.error = "შეიყვანეთ პაროლი"
            tv_register_password.requestFocus()
            return
        }
    }
}