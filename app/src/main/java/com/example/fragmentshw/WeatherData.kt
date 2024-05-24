package com.example.fragmentshw

data class WeatherData(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val feels_like: Double
)

data class Weather(
    val description: String
)

data class Wind(
    val speed: Double
)