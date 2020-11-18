package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.Address
import kotlinx.android.synthetic.main.rv_choose_address_item.view.*

class OrderChooseDetailsAdapter(
    private val context: Context,
    private val onItemClick: (name: String?) -> Unit
) : RecyclerView.Adapter<OrderChooseDetailsAdapter.ViewHolder>() {
    private var list: List<Address> = ArrayList()
    private var mPosition = -1

    fun setAddressList(list: List<Address>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_choose_address_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listModel = list[position]

        holder.addressNameTextView.text = listModel.address

        if (listModel.details != "null") {
            holder.addressDetailsTextView.text = listModel.details
        } else {
            holder.addressDetailsTextView.text = "დაამატეთ მისამართის დეტალები"
        }

        if (mPosition == position) {
            holder.addressRootConstraint.background = ResourcesCompat.getDrawable(context.resources, R.drawable.chosen_address_item_background, null)
            onItemClick(listModel.address)
        } else {
            holder.addressRootConstraint.background = ResourcesCompat.getDrawable(context.resources, R.drawable.choose_address_item_background, null)
        }

        holder.addressRootConstraint.setOnClickListener {
            mPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addressRootConstraint: ConstraintLayout = view.choose_address_item_root
        var addressNameTextView: TextView = view.tv_choose_address_title
        val addressDetailsTextView: TextView = view.tv_choose_address_details
    }
}