package com.example.clima

class DatosClima(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

class Main(
    val temp: Double,
    val humidity: Int
)

class Weather(
    val description: String,
    val icon: String
)