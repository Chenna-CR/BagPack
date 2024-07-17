package com.cr7.bagpack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PackingListAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<PackingListAdapter.PackingListViewHolder>() {

    inner class PackingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {
            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_packing_list, parent, false)
        return PackingListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PackingListViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewItem.text = currentItem
    }

    override fun getItemCount() = itemList.size
}
