package com.example.weatherapp2

import com.example.weatherapp2.CurrentCityName.CurrentCityWeatherReport
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherReportFiveDayThreeHour
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = "https://api.openweathermap.org/"

var retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(CoroutineCallAdapterFactory()).build()

interface RestApiService{

    @GET("data/2.5/onecall?")
    fun getWeatherReport(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String): Deferred<WeatherReport>

    @GET("/data/2.5/forecast?")
    fun getWeatherReportFiveDayThreeHrsReport(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String): Deferred<WeatherReportFiveDayThreeHour>

//    https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={API key}

    @GET("data/2.5/weather?")
    fun getWeatherReportCity(@Query("q") cityNameCountryCode: String, @Query("appid") apiKey: String): Deferred<CurrentCityWeatherReport>

}

object ApiService{
    val restApiService: RestApiService by lazy {
        retrofit.create(RestApiService::class.java)
    }
}