package com.example.clima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var resultTextView: TextView
    lateinit var celcius: TextView

    val apiKey = "7bc46e18fce49f27915e1d7c7e860026"
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApiService = retrofit.create(ApiServicio::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.resultTextView)
        celcius = findViewById(R.id.celcius)


        button.setOnClickListener {
            val cityName = editText.text.toString()
            getWeatherData(cityName)
        }
    }

    private fun getWeatherData(cityName: String) {
        val call = weatherApiService.obtenerClima(cityName, apiKey)

        call.enqueue(object : Callback<DatosClima> {
            override fun onResponse(call: Call<DatosClima>, response: Response<DatosClima>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    if (weatherData != null) {
                        updateUI(weatherData)
                    }
                }
            }

            override fun onFailure(call: Call<DatosClima>, t: Throwable) {
                // Manejar el fallo de la llamada a la API
            }
        })
    }

    private fun updateUI(weatherData: DatosClima) {
        val temperature = weatherData.main.temp
        val humidity = weatherData.main.humidity
        val description = weatherData.weather[0].description
        val iconUrl = "https://openweathermap.org/img/w/${weatherData.weather[0].icon}.png"

        resultTextView.text =
            "Ciudad: ${weatherData.name}\nTemperatura: $temperature °C\nHumedad: $humidity%\nDescripción: $description"

        celcius.text = "$temperature°C"


        val iconImageView: ImageView = findViewById(R.id.iconImageView)

        Picasso.get()
            .load(iconUrl)
            .into(iconImageView)

    }
}





