package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.Address
import kotlinx.android.synthetic.main.rv_addresses_item.view.*

class UserAddressesAdapter(
    private val context: Context,
    private val onDeleteClick: (address: String) -> Unit,
    private val onItemClick: (address: String) -> Unit
) : RecyclerView.Adapter<UserAddressesAdapter.ViewHolder>() {
    private var list: List<Address> = ArrayList()

    fun setAddressList(list : List<Address>) {
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

        holder.addressNameTextView.text = listModel.address

        if (listModel.details != "null") {
            holder.addressDetailsTextView.text = listModel.details
        } else {
            holder.addressDetailsTextView.text = "დაამატეთ მისამართის დეტალები"
        }

        holder.addressItemConstraint.setOnClickListener {
            onItemClick(listModel.id)
        }

        holder.deleteAddressImageView.setOnClickListener {
            onDeleteClick(listModel.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addressItemConstraint: ConstraintLayout = view.addresses_item_root
        val addressNameTextView: TextView = view.tv_user_address_title
        val addressDetailsTextView: TextView = view.tv_user_address_details
        val deleteAddressImageView: ImageView = view.iv_user_address_delete
    }

}