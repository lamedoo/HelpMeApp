package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchooseservices

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import kotlinx.android.synthetic.main.rv_choose_services_item.view.*

class OrderChooseServicesAdapter(
    private val context: Context,
    private val onCounterClick: (serviceName: String, amount: Int) -> Unit
) : RecyclerView.Adapter<OrderChooseServicesAdapter.ViewHolder>() {
    private var list: List<String> = ArrayList()

    fun setServicesList(list: List<String>) {
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
        var counter = 0
        holder.serviceCounterTextView.text = counter.toString()

        holder.serviceTitleTextView.text = listModel
        holder.serviceCounterPlus.setOnClickListener {
            counter++
            holder.serviceCounterTextView.text = counter.toString()
            onCounterClick(listModel, counter)
        }
        holder.serviceCounterMinus.setOnClickListener {
            if (counter != 0) {
                counter--
                holder.serviceCounterTextView.text = counter.toString()
                onCounterClick(listModel, counter)
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