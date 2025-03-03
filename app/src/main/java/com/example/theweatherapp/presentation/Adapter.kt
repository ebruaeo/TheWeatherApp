package com.example.theweatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theweatherapp.data.models.WeeklyWeather
import com.example.theweatherapp.databinding.RecyclerRowBinding
import kotlin.math.roundToInt

class Adapter() : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var weeklyWeatherList: List<WeeklyWeather?> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weeklyWeatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temperature = weeklyWeatherList[position]
        holder.binding.hourOrDayDegreeTextView.text =
            temperature?.main?.weeklyWeatherTemp?.roundToInt().toString() + "°"

    }
}