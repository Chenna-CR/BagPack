package com.cr7.bagpack

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cr7.bagpack.databinding.FragmentTripPlanningBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TripPlanningFragment : Fragment() {

    private lateinit var binding: FragmentTripPlanningBinding
    private lateinit var adapter: TripPlanningAdapter
    private val itemList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripPlanningBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TripPlanningAdapter(itemList)
        binding.recyclerView.adapter = adapter

        binding.fabAddItem.setOnClickListener {
            showAddItemDialog()
        }

        return binding.root
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_trip_item, null)
        val editTextTripItem: EditText = dialogView.findViewById(R.id.editTextTripItem)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Add Trip Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val item = editTextTripItem.text.toString()
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

    private fun updateItem(position: Int, newItem: String) {
        itemList[position] = newItem
        adapter.notifyItemChanged(position)
    }

    private fun deleteItem(position: Int) {
        itemList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}
