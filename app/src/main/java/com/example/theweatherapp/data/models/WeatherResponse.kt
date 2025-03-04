package com.example.theweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeeklyWeatherResponse(
    val list: List<WeatherResponse?>? = null
)

data class WeatherResponse(
    val main: Main? = null,
    @SerializedName("weather")
    val weatherList: List<Weather?>? = null
)

data class Main(
    @SerializedName("temp")
    val weatherTemp: Double? = null
)

data class Weather(
    val icon: String? = null
)
