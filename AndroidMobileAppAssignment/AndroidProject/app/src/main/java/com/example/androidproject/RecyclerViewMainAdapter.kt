package com.example.androidproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewMainAdapter(private var Activity: List<RecyclerViewMainItem>) : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutview_main_card, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtActivity: TextView = itemView.findViewById(R.id.txtActivity)
        val txtCalories: TextView = itemView.findViewById(R.id.txtCalories)
        val txtDateTime: TextView = itemView.findViewById(R.id.txtDateTime)
        val txtDuration: TextView = itemView.findViewById(R.id.txtDuration)
        val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = Activity[position]
        holder.txtActivity.text = ItemsViewModel.activity_name
        holder.txtCalories.text = "Calories Burned: " + ItemsViewModel.calories
        holder.txtDateTime.text = "Date & Time: " + ItemsViewModel.date_time
        holder.txtDuration.text = "Duration(Hours): " + ItemsViewModel.activity_duration
        Picasso.get().load(ItemsViewModel.icon).into(holder.imgIcon)
    }

    override fun getItemCount(): Int {
        return Activity.size
    }

    fun updateList(NewActivities: List<RecyclerViewMainItem>) {
        Activity = NewActivities
        notifyDataSetChanged()
    }

}