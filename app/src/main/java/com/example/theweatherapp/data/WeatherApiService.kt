package com.example.theweatherapp.data

import com.example.theweatherapp.data.models.CurrentWeatherResponse
import com.example.theweatherapp.data.models.WeeklyWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherResponse>


    @GET("forecast")
    suspend fun getWeeklyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("cnt") numberOfDays: Int = 7,
        @Query("units") units: String = "metric"
    ): Response<WeeklyWeatherResponse>










}