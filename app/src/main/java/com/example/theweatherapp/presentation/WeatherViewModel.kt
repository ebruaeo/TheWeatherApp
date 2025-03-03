package com.example.theweatherapp.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweatherapp.data.models.CurrentWeatherResponse
import com.example.theweatherapp.data.models.WeeklyWeatherResponse
import com.example.theweatherapp.utils.RetrofitInstance
import com.example.theweatherapp.utils.Util
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    val weather = MutableLiveData<CurrentWeatherResponse>()
    val _weeklyWeather = MutableLiveData<WeeklyWeatherResponse>()


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getWeather(lat, lon, Util.API_KEY)
            result.body()?.let {
                weather.value = it
                println(it.cityName)

            }
        }
    }


    fun fetchWeeklyWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getWeeklyWeather(lat, lon, Util.API_KEY)
                if (result.isSuccessful) {
                    result.body()?.let {
                        _weeklyWeather.value = it
                        println(it)
                    }
                } else {
                    Log.e("WeatherViewModel", "Error fetching weather: ${result.message()}")
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error: ${e.localizedMessage}")
            }
        }

    }
}