package com.example.weatherapp2

import com.example.weatherapp2.CurrentCityName.CurrentCityWeatherReport
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherReportFiveDayThreeHour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

fun combineWrapperClass(weatherResponse: WeatherReport,message: String): WrapperClass{
    return WrapperClass(weatherResponse,message)
}

data class WrapperClass(
    var data: WeatherReport,
    var message: String
)

fun combineWrapperCurrentCityWeatherReport(weatherResponse: CurrentCityWeatherReport,message: String): WrapperClassCurrentCityWeatherReport {
    return WrapperClassCurrentCityWeatherReport(weatherResponse,message)
}

data class WrapperClassCurrentCityWeatherReport(
    var data: CurrentCityWeatherReport,
    var message: String
)


fun combineWrapperFiveThreeClass(weatherResponse: WeatherReportFiveDayThreeHour,message: String): WrapperClassFiveDayThreeHrs {
    return WrapperClassFiveDayThreeHrs(weatherResponse,message)
}

data class WrapperClassFiveDayThreeHrs(
    var data: WeatherReportFiveDayThreeHour,
    var message: String
)

class WeatherRepository {

    private val failureResponse: WeatherReport ?= null
    private val failureResponseFiveDayThreeHrs: WeatherReportFiveDayThreeHour ?= null
    private val failureResponseCurrentCity: CurrentCityWeatherReport ?= null

    val API_KEY= "4c1feac1eb2810fdff96b335bb289373"
    val latitude = "12.831463782688502"
    val longtitude = "80.04948506013582"


    suspend fun getWeatherResponse(latitudeCityName: String, longtitudeCityName: String): WrapperClass? {

        val response = ApiService.restApiService.getWeatherReport(latitudeCityName,longtitudeCityName,API_KEY)

        return withContext(Dispatchers.IO){

            try {
                val resultData = response.await()
                return@withContext combineWrapperClass(resultData,"Successful")
            }catch (e: Exception){
                return@withContext failureResponse?.let { e.message?.let { it1 -> WrapperClass(it, it1) } }
            }
        }
    }

    suspend fun getFiveDaysThreeHrsResponse(latitudeCityName: String, longtitudeCityName: String): WrapperClassFiveDayThreeHrs?{
        val response = ApiService.restApiService.getWeatherReportFiveDayThreeHrsReport(latitudeCityName, longtitudeCityName,API_KEY)
        return withContext(Dispatchers.IO){
            try {
                val resultData = response.await()
                return@withContext combineWrapperFiveThreeClass(resultData,"Successful")
            }catch (e: Exception){
                return@withContext failureResponseFiveDayThreeHrs?.let { e.message?.let { it1 -> WrapperClassFiveDayThreeHrs(it, it1) } }
            }
        }
    }

    suspend fun getCurrentCityName(cityName: String): WrapperClassCurrentCityWeatherReport?{
        val responseCall = ApiService.restApiService.getWeatherReportCity(cityName,API_KEY)
        return withContext(Dispatchers.IO){
            try{
                val responseData = responseCall.await()
                return@withContext combineWrapperCurrentCityWeatherReport(responseData,"Sucessful")
            }catch (e:Exception){
                return@withContext failureResponseCurrentCity?.let { e.message?.let { it1 -> WrapperClassCurrentCityWeatherReport(it, it1) } }
            }
        }
    }

}