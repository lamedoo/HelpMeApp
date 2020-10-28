package com.lukakordzaia.helpmeapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.lukakordzaia.helpmeapp.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_settings_menu, menu)
    }
}