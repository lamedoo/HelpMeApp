package com.lukakordzaia.helpmeapp.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            signUpUser()
        }
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

        auth.createUserWithEmailAndPassword(tv_register_email.text.toString(), tv_register_password.text.toString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "გთხოვთ გაიაროთ ავტორიზაცია", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(context, "რეგისტრაცია ვერ ხერხდება", Toast.LENGTH_SHORT).show()
                }
            }
    }
}