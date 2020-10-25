package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lukakordzaia.helpmeapp.repository.HelperDetailsRepository

class HelperDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelperDetailsRepository(application)
}