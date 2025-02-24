package com.example.theweatherapp.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.theweatherapp.R
import com.example.theweatherapp.databinding.ActivityMainBinding
import com.example.theweatherapp.utils.RetrofitInstance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindow()
        handleLocationPermission()
        observeViewModel()

        binding.listButton.setOnClickListener {
            val intent = Intent(this, SearchScreenActivity::class.java)
            startActivity(intent)
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = getResources().getColor(R.color.black)
    }

    private fun observeViewModel() {
        viewModel.weather.observe(this) {
            binding.run {
                cityTextView.text = "London"
                degreeTextView.text = it.weatherDetails?.temp?.roundToInt().toString() + "°"
                explanationTextView.text =
                    capitalizeEachWord(it.weatherList?.first()?.description)
                highAndLowTextView.text =
                    "H:${it.weatherDetails?.tempMax?.roundToInt()}° L:${it.weatherDetails?.tempMin?.roundToInt()}°"
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

            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to get location: ${it.message}", Toast.LENGTH_SHORT).show()
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