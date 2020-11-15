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
import kotlinx.android.synthetic.main.rv_choose_address_item.view.*

class OrderChooseDetailsAdapter(
    private val context: Context,
    private val onItemClick: (name: String?) -> Unit
) : RecyclerView.Adapter<OrderChooseDetailsAdapter.ViewHolder>() {
    private var list: List<String> = ArrayList()
    private var mPosition = -1

    fun setAddressList(list: List<String>) {
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

        holder.addressName.text = listModel
        if (mPosition == position) {
            holder.addressRoot.background = ResourcesCompat.getDrawable(context.resources, R.drawable.chosen_address_item_background, null)
            onItemClick(listModel)
        } else {
            holder.addressRoot.background = ResourcesCompat.getDrawable(context.resources, R.drawable.choose_address_item_background, null)
        }

        holder.addressRoot.setOnClickListener {
            mPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addressRoot: ConstraintLayout = view.choose_address_item_root
        var addressName: TextView = view.tv_choose_address_title
    }
}