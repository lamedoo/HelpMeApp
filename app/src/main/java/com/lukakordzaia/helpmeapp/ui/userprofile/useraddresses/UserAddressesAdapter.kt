package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.repository.UserProfileEditRepository
import kotlinx.android.synthetic.main.rv_addresses_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserAddressesAdapter(private val context: Context) : RecyclerView.Adapter<UserAddressesAdapter.ViewHolder>() {
    private var list: List<String> = ArrayList()
    private val currentUser = Firebase.auth.currentUser?.uid
    private val repository = UserProfileEditRepository()

    fun setHelpersList(list : List<String>) {
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

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    GlobalScope.launch {
                        repository.deleteUserAddress(currentUser!!, listModel)
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
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