package com.example.fragmentshw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class DetailWeather: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_weather, container, false)
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiKey = "3f5276a6a692f68256994c102ba506c8"
                val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=Irkutsk&appid=$apiKey&units=metric"
                val stream = URL(weatherURL).openStream()
                val data = stream.bufferedReader().use { it.readText() }

                val gson = Gson()
                val weatherData = gson.fromJson(data, WeatherData::class.java)

                val temperature = weatherData.main.temp
                val feelsLike = weatherData.main.feels_like
                val windSpeed = weatherData.wind.speed
                val description = weatherData.weather.firstOrNull()?.description

                val temperatureTextView: TextView = view.findViewById(R.id.temperatureTextView)
                val feelsLikeTextView: TextView = view.findViewById(R.id.feelsLikeTextView)
                val windSpeedTextView: TextView = view.findViewById(R.id.windSpeedTextView)
                val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)

                val windSpeedText = "Wind speed: $windSpeed m/s"
                val temperatureText = "Temperature: $temperature C"
                val feelsLikeText = "Temperature feels like: $feelsLike C"

                requireActivity().runOnUiThread {
                    temperatureTextView.text = temperatureText
                    feelsLikeTextView.text = feelsLikeText
                    windSpeedTextView.text = windSpeedText
                    descriptionTextView.text = description
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}