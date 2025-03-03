package com.example.theweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeeklyWeatherResponse(
    val list: List<WeeklyWeather?>? = null
)

data class WeeklyWeather(
    val main: Main? = null
)

data class Main(
    @SerializedName("temp")
    val weeklyWeatherTemp: Double? = null
)