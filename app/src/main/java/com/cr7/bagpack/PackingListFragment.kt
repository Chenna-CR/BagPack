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
import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import com.cr7.bagpack.database.TripsDatabase
import com.cr7.bagpack.databinding.FragmentPackingListBinding
import com.cr7.bagpack.dataclasses.TripItemsDataClass

class PackingListFragment : Fragment(), PackingListClick {

    private lateinit var adapter: PackingListAdapter
    private val itemList = mutableListOf<TripItemsDataClass>()

    var tripId = 0
    lateinit var mainActivity: MainActivity
    lateinit var tripsDatabase: TripsDatabase
    lateinit var binding: FragmentPackingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPackingListBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        tripsDatabase = TripsDatabase.getInstance(mainActivity)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PackingListAdapter(itemList, this)
        binding.recyclerView.adapter = adapter

        binding.fabAddPacklist.setOnClickListener {
            showAddItemDialog()
        }

        arguments?.let {
            tripId = it.getInt("tripId")
            getTripItems()
        }

        return binding.root
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, null)
        val editTextItem: EditText = dialogView.findViewById(R.id.editTextItem)
        val btnAdd: Button = dialogView.findViewById(R.id.btnAdd)

        var dialog = Dialog(requireContext())
        dialog.setContentView(dialogView.rootView)
        btnAdd.setOnClickListener {
            val item = editTextItem.text.toString()
            if (item.isNotEmpty()) {
                tripsDatabase.tripsDaoInterface().insertPackingList(TripItemsDataClass(tripId= tripId, packingItem = item ))
                getTripItems()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Item cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }


    fun getTripItems(){
        itemList.clear()
        itemList.addAll(tripsDatabase.tripsDaoInterface().getTripItemsList(tripId))
        adapter.notifyDataSetChanged()
    }

    override fun updateItem(position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, null)
        val editTextItem: EditText = dialogView.findViewById(R.id.editTextItem)
        val btnAdd: Button = dialogView.findViewById(R.id.btnAdd)

        var dialog = Dialog(requireContext())
        dialog.setContentView(dialogView.rootView)
        btnAdd.setOnClickListener {
            val item = editTextItem.text.toString()
            if (item.isNotEmpty()) {
                tripsDatabase.tripsDaoInterface().updateTripPackingItems(TripItemsDataClass(id = itemList[position].id, tripId= tripId, packingItem = item ))
                getTripItems()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Item cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun deleteItem(position: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(R.string.delete_item))
            setMessage(resources.getString(R.string.delete_item_message))
            setPositiveButton(resources.getString(R.string.yes)){_,_->
                tripsDatabase.tripsDaoInterface().deletePackingItems(itemList[position])
                getTripItems()
            }
            setNegativeButton(resources.getString(R.string.no)){_,_->}
            show()
        }
    }
}
