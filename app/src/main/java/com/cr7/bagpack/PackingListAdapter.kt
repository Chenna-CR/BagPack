package com.cr7.bagpack

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cr7.bagpack.dataclasses.TripItemsDataClass

interface PackingListClick{
    fun updateItem(position: Int)
    fun deleteItem(position: Int)
}
class PackingListAdapter(private val itemList: MutableList<TripItemsDataClass>,
                         var packingListClick: PackingListClick) :
    RecyclerView.Adapter<PackingListAdapter.PackingListViewHolder>() {

    inner class PackingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_packing_list, parent, false)
        return PackingListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PackingListViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewItem.text = currentItem.packingItem

        holder.btnEdit.setOnClickListener {
            packingListClick.updateItem(position)
        }

        holder.btnDelete.setOnClickListener {
            packingListClick.deleteItem(position)
        }
    }

    override fun getItemCount() = itemList.size
}
