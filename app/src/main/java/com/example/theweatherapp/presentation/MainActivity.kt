package com.example.theweatherapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.theweatherapp.R
import com.example.theweatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private val viewModel by viewModels<WeatherViewModel>()
    private val weeklyWeatherAdapter = WeeklyWeatherAdapter()
    private val hourlyWeatherAdapter = HourlyWeatherAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindow()
        handleLocationPermission()
        observeCurrentWeather()
        observeHourlyWeather()
        observeWeeklyWeather()


        binding.listButton.setOnClickListener {
            val intent = Intent(this, SearchScreenActivity::class.java)
            startActivity(intent)
        }

        binding.recyclerView.adapter = hourlyWeatherAdapter

        setHourlyClickListener()
        setWeeklyClickListener()

    }

    private fun setHourlyClickListener() {
        binding.run {
            hourlyWeatherTextView.setOnClickListener {
                recyclerView.adapter = hourlyWeatherAdapter
                hourlyWeatherTextView.setTextColor(resources.getColor(R.color.textColorWhite))
                weeklyWeatherTextView.setTextColor(resources.getColor(R.color.textColorGray))

            }
        }
    }

    private fun setWeeklyClickListener() {
        binding.run {
            weeklyWeatherTextView.setOnClickListener {
                recyclerView.adapter = weeklyWeatherAdapter
                weeklyWeatherTextView.setTextColor(resources.getColor(R.color.textColorWhite))
                hourlyWeatherTextView.setTextColor(resources.getColor(R.color.textColorGray))
            }
        }
    }

    private fun handleLocationPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkLocationPermission()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }


    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun setWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        window.navigationBarColor = getResources().getColor(R.color.black)
    }

    private fun observeCurrentWeather() {
        viewModel.weather.observe(this) {
            binding.run {
                cityTextView.text = it.cityName
                degreeTextView.text =
                    it.currentWeatherDetails?.temp?.roundToInt().toString() + "°"
                explanationTextView.text =
                    capitalizeEachWord(it.currentWeatherList?.first()?.description)
                highAndLowTextView.text =
                    "H:${it.currentWeatherDetails?.tempMax?.roundToInt()}° L:${it.currentWeatherDetails?.tempMin?.roundToInt()}°"
            }

        }
    }

    private fun observeWeeklyWeather() {
        viewModel._weeklyWeather.observe(this) { response ->
            if (response.list != null) {
                weeklyWeatherAdapter.weatherResponseList = response.list
            }
        }
    }

    private fun observeHourlyWeather() {
        viewModel._hourlyWeather.observe(this) { response ->
            if (response.list != null) {
                hourlyWeatherAdapter.hourlyWeatherList = response.list
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Permission Granted → Fetch Location
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                //  Permission Denied → Show Message
                showDialog()

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                Log.d("Location", "Lat: $lat, Lon: $lon")

                viewModel.fetchWeather(lat, lon)
                viewModel.fetchHourlyWeather(lat, lon)
                viewModel.fetchWeeklyWeather(lat, lon)
            } else {
                Toast.makeText(
                    this,
                    "Unknown location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Failed to get location: ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setMessage("You cannot use this application without location permission")
            .setTitle("Permission Required")
            .setNeutralButton(
                "Ok"
            ) { dialog, which -> finish() }

            .create()
            .show()
    }

    private fun capitalizeEachWord(text: String?): String? {
        return text?.split(" ")
            ?.joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
    }
}