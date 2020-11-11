package com.lukakordzaia.helpmeapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.rv_top_helpers_item.view.*

class HomeAdapter(
    private val context: Context,
    private val onItemClick: (id: Int) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var list: List<Helpers> = ArrayList()

    fun setTopHelpersList(list : List<Helpers>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_top_helpers_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listModel = list[position]

        Picasso.get().load(listModel.avatar).into(holder.avatar)
        holder.name.text = listModel.name
        holder.rating.text = listModel.rating.toString()
        holder.rootView.setOnClickListener {
            onItemClick(listModel.id)
        }
    }

    override fun getItemCount(): Int {
//        val limit = 4
//        return if (list.size > limit) {
//            limit
//        } else {
//            list.size
//        }
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.top_helper_item_root
        val avatar: CircleImageView = view.iv_top_helper_avatar
        val name: TextView = view.tv_top_helper_name
        val rating: TextView = view.tv_top_helper_rating
    }

}