package com.example.weatherapp2

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CurrentWeatherBottomSheet(viewModelFromActivity: WeatherViewModel): BottomSheetDialogFragment() {

    var viewModel: WeatherViewModel = viewModelFromActivity


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_current_weather_data,container, false)


//        viewModel.getWeatherReponse()
        val clouds = view.findViewById<TextView>(R.id.current_clouds_text_data)
        val dewPoint = view.findViewById<TextView>(R.id.current_dew_point_text_data)
        val date = view.findViewById<TextView>(R.id.current_dt_text_data)
        val feelsLike = view.findViewById<TextView>(R.id.current_feels_like_text_data)
        val pressure = view.findViewById<TextView>(R.id.current_pressure_text_data)
        val humidity = view.findViewById<TextView>(R.id.current_humidity_text_data)
        val temperature = view.findViewById<TextView>(R.id.current_temp_text_data)
        val sunrise = view.findViewById<TextView>(R.id.current_sunrise_text_data)
        val sunset = view.findViewById<TextView>(R.id.current_sunset_text_data)
        val uvi = view.findViewById<TextView>(R.id.current_uvi_text_data)
        val visibility = view.findViewById<TextView>(R.id.current_visibility_text_data)
        val windDeg = view.findViewById<TextView>(R.id.current_wind_deg_text_data)
        val windGust = view.findViewById<TextView>(R.id.current_wind_gust_text_data)
        val windSpeed = view.findViewById<TextView>(R.id.current_wind_speed_text_data)
        val id = view.findViewById<TextView>(R.id.current_id_text_data)
        val main = view.findViewById<TextView>(R.id.current_main_text_data)
        val description = view.findViewById<TextView>(R.id.current_description_text_data)
        val icon = view.findViewById<TextView>(R.id.current_icon_text_data)


        viewModel.reponse.observe(this.viewLifecycleOwner, Observer {report ->

            if (report == null){
                Log.i("TestingApp","Data Response --> null")
            }else {

                val outputFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS")
                outputFormatter.timeZone = TimeZone.getTimeZone("UTC/GMT +5:30 hours")
                outputFormatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

                val sunriseTime: String = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(
                    Instant.ofEpochSecond(report.report.current.sunrise.toLong()).toEpochMilli()
                )
                val sunsetTime: String = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(
                    Instant.ofEpochSecond(report.report.current.sunset.toLong()).toEpochMilli()
                )
                val dateTime: String = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(
                    Instant.ofEpochSecond(report.report.current.dt.toLong()).toEpochMilli()
                )

                clouds.text = report.report.current.clouds.toString()

                date.text = dateTime

                pressure.text = report.report.current.pressure.toString() + "hPa"
                humidity.text = report.report.current.humidity.toString() + "%"

                sunrise.text = sunriseTime
                sunset.text = sunsetTime
                uvi.text = report.report.current.uvi.toString()
                visibility.text = (report.report.current.visibility / 1000).toString() + "km"
                windDeg.text = report.report.current.windDeg.toString() + "\u00B0"
                windGust.text = (((report.report.current.windGust * 3600) / 1000).roundToInt()).toString() + "km/hr"
                windSpeed.text = (((report.report.current.windSpeed * 3600) / 1000).roundToInt()).toString() + "km/hr"
                id.text = report.report.current.weather[0].id.toString()
                main.text = report.report.current.weather[0].main
                description.text = report.report.current.weather[0].description
                icon.text = report.report.current.weather[0].icon

                if(viewModel.temperatureState.value == "Celsius"){
                    feelsLike.text = ((report.report.current.feelsLike - 273.5).roundToLong()).toString() + "℃"
                    temperature.text = ((report.report.current.temp - 273.5).roundToLong()).toString() + "℃"
                    dewPoint.text = ((report.report.current.dewPoint - 273.5).roundToLong()).toString() + "℃"

                }else if (viewModel.temperatureState.value == "Fahrenheit"){
                    feelsLike.text = ((((report.report.current.feelsLike - 273.5)* 1.8) + 32).roundToLong()).toString() + "℉"
                    temperature.text = ((((report.report.current.temp - 273.5)* 1.8) + 32).roundToLong()).toString() + "℉"
                    dewPoint.text = ((((report.report.current.dewPoint - 273.5)* 1.8) + 32).roundToLong()).toString() + "℉"
                }

            }
        })

        return view
    }

}