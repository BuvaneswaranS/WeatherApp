package com.example.weatherapp2

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.Instant
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class WeatherDailyAdapter(viewModel: WeatherViewModel): ListAdapter<Daily, WeatherDailyAdapter.ViewHolder>(weatherDailyDataCallBack()) {

    var viewModel: WeatherViewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDailyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_weather_data_daily, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherDailyAdapter.ViewHolder, position: Int) {
        val response = getItem(position)
        holder.bindDataToRecyclerViewItems(response,position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var date: TextView
        var temperature: TextView
        var sunriseTime: TextView
        var sunsetTime: TextView
        var weatherIcon: ImageView
        var pressure: TextView
        var feelsLike: TextView
        var humidity: TextView
        var windSpeed: TextView

        init {
            date = itemView.findViewById(R.id.weather_daily_date)
            temperature = itemView.findViewById(R.id.weather_daily_temperature)
            sunriseTime = itemView.findViewById(R.id.weather_daily_sunrise_time)
            sunsetTime = itemView.findViewById(R.id.weather_daily_sunset_time)
            weatherIcon = itemView.findViewById(R.id.weather_icon)
            pressure = itemView.findViewById(R.id.weather_daily_pressure_text_area)
            feelsLike = itemView.findViewById(R.id.weather_daily_feels_like_text_area)
            humidity = itemView.findViewById(R.id.weather_daily_humidity_text_area)
            windSpeed = itemView.findViewById(R.id.weather_daily_wind_text_area)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bindDataToRecyclerViewItems(response: Daily, position: Int){
            val datetoday: String = SimpleDateFormat("LLLL dd").format(Instant.ofEpochSecond(response.dt.toLong()).toEpochMilli())
            val sunriseTimeDisplay: String = SimpleDateFormat("HH:mm:ss").format(Instant.ofEpochSecond(response.sunrise.toLong()).toEpochMilli())
            val sunsetTimeDisplay: String = SimpleDateFormat("HH:mm:ss").format(Instant.ofEpochSecond(response.sunset.toLong()).toEpochMilli())

            date.text = datetoday
            sunriseTime.text = sunriseTimeDisplay
            sunsetTime.text = sunsetTimeDisplay
            pressure.text = response.pressure.toString() + "hPa"
            humidity.text = response.humidity.toString() + "%"
            windSpeed.text = (((response.windSpeed * 3600) / 1000).roundToInt()).toString() + "km/hr"

            if (viewModel.temperatureState.value == "Celsius"){
                temperature.text =  ((response.temp.day - 273.15).roundToLong()).toString() + "\u2103"
                feelsLike.text = ((response.feelsLike.day - 273.15).roundToLong()).toString() + "\u2103"
            }else if (viewModel.temperatureState.value == "Fahrenheit"){
                temperature.text = ((((response.temp.day - 273.15)* 1.8) + 32).roundToLong()).toString() + "\u2109"
                feelsLike.text = ((((response.feelsLike.day - 273.15)* 1.8) + 32).roundToLong()).toString() + "\u2109"
            }

            when (response.weather[0].icon){
                "01d" -> {
                    weatherIcon.setImageResource(R.mipmap.day01_round)
                }
                "02d" -> {
                    weatherIcon.setImageResource(R.mipmap.day02_round)
                }
                "03d" -> {
                    weatherIcon.setImageResource(R.mipmap.day03_round)
                }
                "04d" -> {
                    weatherIcon.setImageResource(R.mipmap.day04)
                }

                "09d" -> {
                    weatherIcon.setImageResource(R.mipmap.day09_round)
                }
                "10d" -> {
                    weatherIcon.setImageResource(R.mipmap.day10_round)
                }

                "11d" -> {
                    weatherIcon.setImageResource(R.mipmap.day11_round)
                }
            }
        }

    }


}

class weatherDailyDataCallBack: DiffUtil.ItemCallback<Daily>(){
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return  areItemsTheSame(oldItem, newItem)
    }

}