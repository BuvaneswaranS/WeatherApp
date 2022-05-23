package com.example.weatherapp2.five_days_three_hour_forecast


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String
)