package com.cr7.bagpack

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenseTrackerFragment : Fragment() {
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenseList = mutableListOf<Expense>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_tracker, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        expenseAdapter = ExpenseAdapter(expenseList)
        recyclerView.adapter = expenseAdapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab_add_expence)
        fab.setOnClickListener {
            showAddExpenseDialog()
        }

        return view
    }

    private fun showAddExpenseDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_expense, null)
        val editTextExpenseType: EditText = dialogView.findViewById(R.id.editTextExpenseType)
        val editTextExpenseAmount: EditText = dialogView.findViewById(R.id.editTextExpenseAmount)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Add Expense")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val type = editTextExpenseType.text.toString()
                val amount = editTextExpenseAmount.text.toString().toDoubleOrNull()

                if (type.isNotEmpty() && amount != null) {
                    expenseList.add(Expense(type, amount))
                    expenseAdapter.notifyItemInserted(expenseList.size - 1)
                } else {
                    Toast.makeText(context, "Please enter valid data", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
