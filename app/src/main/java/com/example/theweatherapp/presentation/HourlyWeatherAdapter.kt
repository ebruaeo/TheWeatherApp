package com.example.theweatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theweatherapp.data.models.WeatherResponse
import com.example.theweatherapp.databinding.RecyclerRowBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {
    var hourlyWeatherList: List<WeatherResponse?> = listOf()
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
        return hourlyWeatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyWeather = hourlyWeatherList[position]
        holder.binding.hourOrDayDegreeTextView.text =
            hourlyWeather?.main?.weatherTemp?.roundToInt().toString() + "°"

        holder.binding.hourOrDayTextView.text = get24HoursFormatted("hh a").get(position * 3)

        val url =
            "https://openweathermap.org/img/wn/${hourlyWeather?.weatherList?.firstOrNull()?.icon}@2x.png"
        Glide.with(holder.binding.root).load(url)
            .into(holder.binding.hourOrDayWeatherImage)

    }

    fun get24HoursFormatted(pattern: String): List<String> {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return (0..23).map { LocalTime.of(it, 0).format(formatter) }
    }

}