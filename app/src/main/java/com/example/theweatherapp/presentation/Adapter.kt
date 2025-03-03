package com.example.theweatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theweatherapp.data.models.WeeklyWeather
import com.example.theweatherapp.databinding.RecyclerRowBinding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
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
        val weeklyWeather = weeklyWeatherList[position]
        holder.binding.hourOrDayDegreeTextView.text =
            weeklyWeather?.main?.weeklyWeatherTemp?.roundToInt().toString() + "°"

        holder.binding.hourOrDayTextView.text = getNext7DaysOfWeek().get(position % 7)

        val url =
            "https://openweathermap.org/img/wn/${weeklyWeather?.weatherList?.firstOrNull()?.icon}@2x.png"
        Glide.with(holder.binding.root).load(url)
            .into(holder.binding.hourOrDayWeatherImage)

    }

    fun getNext7DaysOfWeek(locale: Locale = Locale.getDefault()): List<String> {
        val today = LocalDate.now()
        return (0 until 7).map {
            today.plusDays(it.toLong()).dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
        }
    }


}
