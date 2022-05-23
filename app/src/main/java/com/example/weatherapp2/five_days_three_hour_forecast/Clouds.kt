package com.example.weatherapp2.five_days_three_hour_forecast


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)