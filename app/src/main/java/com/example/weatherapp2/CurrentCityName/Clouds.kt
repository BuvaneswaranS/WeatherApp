package com.example.weatherapp2.CurrentCityName


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)