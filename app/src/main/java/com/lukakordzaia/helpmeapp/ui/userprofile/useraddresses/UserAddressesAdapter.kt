package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.rv_addresses_item.view.*

class UserAddressesAdapter(
    private val context: Context,
    private val onItemClick: (address: String) -> Unit
) : RecyclerView.Adapter<UserAddressesAdapter.ViewHolder>() {
    private var list: List<String> = ArrayList()

    fun setAddressList(list : List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_addresses_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listModel = list[position]

        holder.addressNameTextView.text = listModel
        holder.deleteAddressImageView.setOnClickListener {
            onItemClick(listModel)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addressNameTextView: TextView = view.tv_user_address_title
        val deleteAddressImageView: ImageView = view.iv_user_address_delete
    }

}