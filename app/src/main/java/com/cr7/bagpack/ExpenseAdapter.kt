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

class ExpenseAdapter(private val expenseList: MutableList<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewType: TextView = itemView.findViewById(R.id.textViewType)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {
            btnEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditExpenseDialog(position, itemView)
                }
            }

            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    expenseList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentExpense = expenseList[position]
        holder.textViewType.text = currentExpense.type
        holder.textViewAmount.text = currentExpense.amount.toString()
    }

    override fun getItemCount() = expenseList.size

    private fun showEditExpenseDialog(position: Int, itemView: View) {
        val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_add_expense, null)
        val editTextExpenseType: EditText = dialogView.findViewById(R.id.editTextExpenseType)
        val editTextExpenseAmount: EditText = dialogView.findViewById(R.id.editTextExpenseAmount)
        val currentExpense = expenseList[position]

        editTextExpenseType.setText(currentExpense.type)
        editTextExpenseAmount.setText(currentExpense.amount.toString())

        val dialog = AlertDialog.Builder(itemView.context)
            .setTitle("Edit Expense")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val type = editTextExpenseType.text.toString()
                val amount = editTextExpenseAmount.text.toString().toDoubleOrNull()

                if (type.isNotEmpty() && amount != null) {
                    expenseList[position] = Expense(type, amount)
                    notifyItemChanged(position)
                } else {
                    Toast.makeText(itemView.context, "Please enter valid data", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
