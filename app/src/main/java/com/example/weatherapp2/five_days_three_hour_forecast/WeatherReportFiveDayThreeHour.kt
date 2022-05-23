package com.example.weatherapp2.five_days_three_hour_forecast


import com.google.gson.annotations.SerializedName

data class WeatherReportFiveDayThreeHour(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherList>,
    @SerializedName("message")
    val message: Int
)