package com.cr7.bagpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.app.AlertDialog
import android.widget.EditText

class PackingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PackingListAdapter
    private val itemList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_packing_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PackingListAdapter(itemList)
        recyclerView.adapter = adapter

        val fabAddItem: FloatingActionButton = view.findViewById(R.id.fab_add_packlist)
        fabAddItem.setOnClickListener {
            showAddItemDialog()
        }

        return view
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, null)
        val editTextItem: EditText = dialogView.findViewById(R.id.editTextItem)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Add Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val item = editTextItem.text.toString()
                if (item.isNotEmpty()) {
                    addItem(item)
                } else {
                    Toast.makeText(context, "Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun addItem(item: String) {
        itemList.add(item)
        adapter.notifyItemInserted(itemList.size - 1)
    }

    private fun deleteItem(position: Int) {
        itemList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}
