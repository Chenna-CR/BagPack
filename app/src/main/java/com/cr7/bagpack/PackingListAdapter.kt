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

class PackingListAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<PackingListAdapter.PackingListViewHolder>() {

    inner class PackingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {
            btnEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditItemDialog(position, itemView)
                }
            }

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

    private fun showEditItemDialog(position: Int, itemView: View) {
        val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_add_item, null)
        val editTextItem: EditText = dialogView.findViewById(R.id.editTextItem)
        editTextItem.setText(itemList[position])

        val dialog = AlertDialog.Builder(itemView.context)
            .setTitle("Edit Item")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newItem = editTextItem.text.toString()
                if (newItem.isNotEmpty()) {
                    itemList[position] = newItem
                    notifyItemChanged(position)
                } else {
                    Toast.makeText(itemView.context, "Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
