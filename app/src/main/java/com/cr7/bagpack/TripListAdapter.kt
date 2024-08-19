package com.cr7.bagpack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.cr7.bagpack.dataclasses.TripDataClass

class TripListAdapter(var context: Context,var tripList:ArrayList<TripDataClass>, var clickInterface:onClick):RecyclerView.Adapter<TripListAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var itemname=view.findViewById<TextView>(R.id.textViewItem)
        var update=view.findViewById<ImageButton>(R.id.btnEdit)
        var delete=view.findViewById<ImageButton>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListAdapter.ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.trip_list_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripListAdapter.ViewHolder, position: Int) {
        holder.itemname.setText(tripList[position].name)
        holder.update.setOnClickListener{
            clickInterface.update(position)
        }
        holder.delete.setOnClickListener{
            clickInterface.delete(position)
        }

    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    interface onClick{
        fun delete(position: Int)
        fun update(position: Int)
    }
}