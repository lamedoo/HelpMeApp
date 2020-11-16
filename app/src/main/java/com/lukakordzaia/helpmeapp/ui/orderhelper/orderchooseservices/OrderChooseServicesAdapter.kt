package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchooseservices

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.ServicesList
import kotlinx.android.synthetic.main.rv_choose_services_item.view.*

class OrderChooseServicesAdapter(
    private val context: Context,
    private val onCounterClick: (serviceName: String, amount: Int) -> Unit
) : RecyclerView.Adapter<OrderChooseServicesAdapter.ViewHolder>() {
    private var list: MutableList<ServicesList> = ArrayList()

    fun setServicesList(list: MutableList<ServicesList>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_choose_services_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listModel = list[position]

        holder.serviceTitleTextView.text = listModel.serviceName
        holder.serviceCounterTextView.text = listModel.serviceCount.toString()

        holder.serviceCounterPlus.setOnClickListener {
            listModel.serviceCount++
            holder.serviceCounterTextView.text = listModel.serviceCount.toString()
            onCounterClick(listModel.serviceName, listModel.serviceCount)
        }
        holder.serviceCounterMinus.setOnClickListener {
            if (listModel.serviceCount != 0) {
                listModel.serviceCount--
                holder.serviceCounterTextView.text = listModel.serviceCount.toString()
                onCounterClick(listModel.serviceName, listModel.serviceCount)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceTitleTextView: TextView = view.service_title
        val serviceCounterTextView: TextView = view.service_counter
        val serviceCounterPlus: ImageView = view.service_plus
        val serviceCounterMinus: ImageView = view.service_minus
    }

}