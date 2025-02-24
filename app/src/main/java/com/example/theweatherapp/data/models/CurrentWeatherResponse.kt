package com.example.theweatherapp.data.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("weather")
    val currentWeatherList: List<CurrentWeather?>? = null,
    @SerializedName("main")
    val currentWeatherDetails: CurrentWeatherDetails? = null,
    @SerializedName("name")
    val cityName: String? = null
)

data class CurrentWeather(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class CurrentWeatherDetails(
    val temp: Float? = null,
    @SerializedName("temp_min")
    val tempMin: Float? = null,
    @SerializedName("temp_max")
    val tempMax: Float? = null
)


