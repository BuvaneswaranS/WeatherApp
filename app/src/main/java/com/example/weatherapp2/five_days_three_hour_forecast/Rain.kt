package com.example.weatherapp2.five_days_three_hour_forecast


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)