package com.lukakordzaia.helpmeapp.ui.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.ui.helperdetails.HelperDetailsFragment
import kotlinx.android.synthetic.main.rv_helpers_item.view.*

class HelpersAdapter(private val context: Context, private val findNavController: NavController) : RecyclerView.Adapter<HelpersAdapter.ViewHolder>()
{
    private var list: List<Helpers> = ArrayList()

    fun setHelpersList(list : List<Helpers>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_helpers_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listModel = list[position]

        holder.name.text = listModel.name
        holder.prefLocation.text = listModel.address.city
        holder.rootView.setOnClickListener {
            val bundle = bundleOf("helperId" to listModel.id)
            findNavController.navigate(R.id.action_helpersFragment_to_helperDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.tv_helper_name!!
        val prefLocation = view.tv_helper_preferred_location!!
        val rootView = view.item_root
    }
}