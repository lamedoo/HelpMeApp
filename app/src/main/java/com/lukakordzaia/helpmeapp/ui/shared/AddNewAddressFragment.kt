package com.lukakordzaia.helpmeapp.ui.shared

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses.UserAddressesViewModel
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.createToast

class AddNewAddressFragment : Fragment(R.layout.fragment_add_new_address) {
    private lateinit var viewModel: UserAddressesViewModel

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(requireContext(), getString(R.string.google_maps_api_key))
        Places.createClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserAddressesViewModel::class.java)


            val fields = listOf(Place.Field.ID, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(requireContext())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        place.name?.let { viewModel.addAddressToFirestore(it) }
                        viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
                            context.createToast(it)
                        })
                        requireActivity().onBackPressed()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        status.statusMessage?.let { it1 -> Log.i(ContentValues.TAG, it1) }
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}