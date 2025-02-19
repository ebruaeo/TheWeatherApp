package com.example.theweatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherapp.databinding.RecyclerRowBinding

class Adapter(private val itemList: List<Weather>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.hourTextView.text = itemList[position].time
        holder.binding.hourlyWeatherImage.setImageResource(itemList[position].weatherIcon)
        holder.binding.hourlyDegreeTextView.text = itemList[position].degree
    }

}