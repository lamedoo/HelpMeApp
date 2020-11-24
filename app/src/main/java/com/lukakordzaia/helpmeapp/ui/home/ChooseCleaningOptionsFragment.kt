package com.lukakordzaia.helpmeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import kotlinx.android.synthetic.main.fragment_choose_cleaning_options.*

class ChooseCleaningOptionsFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_cleaning_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {navigation ->
            navController(navigation)
        })

        tv_choose_cleaning_renovation.setOnClickListener {
            viewModel.onChooseServicesPressed("Renovation")

        }
        tv_choose_cleaning_standard.setOnClickListener {
            viewModel.onChooseServicesPressed("Standard")
        }
    }
}