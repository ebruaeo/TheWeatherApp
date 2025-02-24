package com.example.theweatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherapp.databinding.RecyclerRowBinding
import com.example.theweatherapp.data.models.CurrentWeatherResponse

class Adapter(private val currentWeatherResponse: CurrentWeatherResponse?=null) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentWeatherResponse?.currentWeatherList?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = currentWeatherResponse?.currentWeatherList?.get(position)
        holder.binding.run {
            hourTextView.text = weather?.main
        }
    }

}