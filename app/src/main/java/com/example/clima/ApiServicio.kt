package com.example.clima
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicio {
    @GET("weather")
    fun obtenerClima(
        @Query("q") ciudad: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<DatosClima>
}