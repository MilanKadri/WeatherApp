package com.example.weathermonkey

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.example.weathermonkey.data.repository.WeatherRepositoryInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel(
    application: Application,
    private val weatherRepository: WeatherRepositoryInterface,
) : AndroidViewModel(application) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _temperature = MutableStateFlow<String?>(null)
    val temperature: StateFlow<String?> = _temperature.asStateFlow()

    suspend fun fetchTemperatureByLocation(latitude: Double, longitude: Double) {
        try {
            val weatherData = weatherRepository.fetchCurrentWeatherData(
                latitude = latitude,
                longitude = longitude,
                forecast_days = 1
            )

            val temperatureMax = weatherData.daily.temperature2mMax.firstOrNull()
            val temperatureMin = weatherData.daily.temperature2mMin.firstOrNull()

            if (temperatureMax != null && temperatureMin != null) {
                _temperature.value = "Max: $temperatureMax°C, Min: $temperatureMin°C"
            } else {
                _temperature.value = "Keine Temperaturdaten verfügbar"
            }
        } catch (e: Exception) {
            _temperature.value = "Fehler: ${e.message}"
        }
    }

//TODO: locationRepository
    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            _location.value = location
        }.addOnFailureListener {
            _location.value = null
        }
    }
}
