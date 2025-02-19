package com.example.theweatherapp

import java.io.Serializable

object WeatherData: Serializable {

    val weatherList = mutableListOf(
        Weather("1 AM", R.drawable.moon_cloud_fast_wind, "17°"),
        Weather("2 AM", R.drawable.mooncloudmidrain, "13°"),
        Weather("3 AM", R.drawable.suncloudangledrain, "17°"),
        Weather("4 AM", R.drawable.suncloudmidrain, "21°"),
        Weather("5 AM", R.drawable.moon_cloud_fast_wind, "17°"),
        Weather("6 AM", R.drawable.suncloudangledrain, "25°"),
        Weather("7 AM", R.drawable.suncloudmidrain, "17°"),
        Weather("8 AM", R.drawable.moon_cloud_fast_wind, "27°"),
        Weather("9 AM", R.drawable.tornado, "5°"),
        Weather("10 AM", R.drawable.moon_cloud_fast_wind, "17°"),
        Weather("11 AM", R.drawable.tornado, "8°"),
        Weather("12 AM", R.drawable.moon_cloud_fast_wind, "22°"),
        Weather("1 PM", R.drawable.tornado, "10°"),
    )


}