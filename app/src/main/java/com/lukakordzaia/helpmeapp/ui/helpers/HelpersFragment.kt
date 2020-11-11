package com.lukakordzaia.helpmeapp.ui.helpers

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.ui.MainActivity
import com.lukakordzaia.helpmeapp.utils.EventObserver
import com.lukakordzaia.helpmeapp.utils.navController
import com.lukakordzaia.helpmeapp.utils.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_helpers.*

class HelpersFragment : Fragment(R.layout.fragment_helpers) {
    private lateinit var viewModel: HelpersViewModel
    private lateinit var adapter: HelpersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpersViewModel::class.java)

        viewModel.getAllHelpers()
        adapter = HelpersAdapter(requireContext()) { helperId ->
            viewModel.onHelperPressed()
            val bundle = bundleOf("helperId" to helperId)
            viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
                navController(it, bundle)
            })
        }
        rv_helpers.adapter = adapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            pb_helpers.setVisibleOrGone(it)
        })

        viewModel.helpersList.observe(viewLifecycleOwner, Observer {
            adapter.setHelpersList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.helpers_filter_menu, menu)

        val search = menu.findItem(R.id.helpers_filter_bySearch)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterWithSearch(query!!)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    if (query.isEmpty()) {
                        viewModel.getAllHelpers()
                    }
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.helpers_filter_byRating) {
            viewModel.getHelpersByRating()
            return true
        } else if (id == R.id.helpers_filter_byPrice) {
            viewModel.getHelpersByPrice()
            return true
        }

        return super.onOptionsItemSelected(item)
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