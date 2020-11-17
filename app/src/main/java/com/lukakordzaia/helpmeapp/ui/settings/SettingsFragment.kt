package com.lukakordzaia.helpmeapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var viewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        if (AppPreferences.dark_mode) {
            switch_settings_theme.isChecked = true
        }

        switch_settings_theme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.switchIsChecked(requireContext())
            } else {
                viewModel.switchIsNotChecked(requireContext())
            }
        }
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