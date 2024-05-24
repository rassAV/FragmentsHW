package com.example.fragmentshw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class ShortWeather: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.short_weather, container, false)
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
                val description = weatherData.weather.firstOrNull()?.description

                val temperatureTextView: TextView = view.findViewById(R.id.temperatureTextView)
                val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)

                val temperatureText = "Temperature: $temperature °C"

                requireActivity().runOnUiThread {
                    temperatureTextView.text = temperatureText
                    descriptionTextView.text = description
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
