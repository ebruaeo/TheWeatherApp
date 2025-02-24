package com.example.theweatherapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweatherapp.data.models.CurrentWeatherResponse
import com.example.theweatherapp.utils.RetrofitInstance
import com.example.theweatherapp.utils.Util
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    val weather = MutableLiveData<CurrentWeatherResponse>()

     fun fetchWeather(lat: Double, lon: Double){
        viewModelScope.launch {
          val result = RetrofitInstance.api.getWeather(lat,lon,Util.API_KEY)
            result.body()?.let {
                weather.value= it
            }
        }
    }
}