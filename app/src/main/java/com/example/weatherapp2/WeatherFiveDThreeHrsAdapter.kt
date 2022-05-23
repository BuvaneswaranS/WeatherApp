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
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherList
import java.text.SimpleDateFormat
import java.time.Instant
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class WeatherFiveDThreeHrsAdapter(viewModel: WeatherViewModel): ListAdapter<WeatherList, WeatherFiveDThreeHrsAdapter.ViewHolder>(weatherFiveDThreeHrsCallBack()) {

    var weatherViewModel: WeatherViewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFiveDThreeHrsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_weather_data,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherFiveDThreeHrsAdapter.ViewHolder, position: Int) {
        val response = getItem(position)
        holder.bindData(response,position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var temperature: TextView
        var date: TextView
        var time: TextView
        var weatherIcon: ImageView
        var pressure: TextView
        var feelsLike: TextView
        var humidity: TextView
        var windSpeed: TextView

        init {
            date = itemView.findViewById(R.id.weather_hourly_date)
            time = itemView.findViewById(R.id.weather_hourly_date_time)
            temperature = itemView.findViewById(R.id.weather_hourly_temperature)
            weatherIcon = itemView.findViewById(R.id.weather_icon)
            pressure = itemView.findViewById(R.id.weather_hourly_pressure_text_area)
            feelsLike = itemView.findViewById(R.id.weather_hourly_feels_like_text_area)
            humidity = itemView.findViewById(R.id.weather_hourly_humidity_text_area)
            windSpeed = itemView.findViewById(R.id.weather_hourly_wind_text_area)
        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(response: WeatherList, position: Int){
            val datetoday: String = SimpleDateFormat("LLLL dd").format(Instant.ofEpochSecond(response.dt.toLong()).toEpochMilli())
            val timeToday: String = SimpleDateFormat("HH:mm:ss").format(Instant.ofEpochSecond(response.dt.toLong()).toEpochMilli())

            date.text = datetoday
            time.text = timeToday
            pressure.text = response.main.pressure.toString() + "hPa"
            humidity.text = response.main.humidity.toString() + "%"
            windSpeed.text = (((response.wind.speed * 3600) / 1000).roundToInt()).toString() + "km/hr"

            if (weatherViewModel.temperatureState.value == "Celsius"){

                feelsLike.text = ((response.main.feelsLike - 273.15).roundToLong()).toString() + "℃"
                temperature.text =  ((response.main.temp - 273.15).roundToLong()).toString() + "℃"

            }else if (weatherViewModel.temperatureState.value == "Fahrenheit"){
                feelsLike.text = ((((response.main.feelsLike - 273.15)* 1.8) + 32).roundToLong()).toString() + "℉"
                temperature.text = ((((response.main.temp - 273.15) * 1.8)+ 32).roundToLong()).toString() + "℉"
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

                "01n" -> {
                    weatherIcon.setImageResource(R.mipmap.night01_round)
                }

                "02n" -> {
                    weatherIcon.setImageResource(R.mipmap.night02_round)
                }

                "03n" -> {
                    weatherIcon.setImageResource(R.mipmap.night03_round)
                }

                "04n" -> {
                    weatherIcon.setImageResource(R.mipmap.night04_round)
                }

                "09n" -> {
                    weatherIcon.setImageResource(R.mipmap.night09_round)
                }

                "10n" -> {
                    weatherIcon.setImageResource(R.mipmap.night10_round)
                }

                "11n" -> {
                    weatherIcon.setImageResource(R.mipmap.night11_round)
                }


            }

        }



    }
}

class weatherFiveDThreeHrsCallBack: DiffUtil.ItemCallback<WeatherList>(){
    override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}