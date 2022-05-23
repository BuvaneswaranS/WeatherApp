package com.example.weatherapp2

import com.example.weatherapp2.five_days_three_hour_forecast.WeatherReportFiveDayThreeHour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

fun combineWrapperClass(weatherResponse: WeatherReport,message: String): WrapperClass{
    return WrapperClass(weatherResponse,message)
}

data class WrapperClass(
    var data: WeatherReport,
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

    var failureResponse: WeatherReport ?= null
    var failureResponseFiveDayThreeHrs: WeatherReportFiveDayThreeHour ?= null

    val API_KEY= "4c1feac1eb2810fdff96b335bb289373"
    val latitude = "12.831463782688502"
    val longtitude = "80.04948506013582"


    suspend fun getWeatherResponse(): WrapperClass? {


        val response = ApiService.restApiService.getWeatherReport(latitude,longtitude,API_KEY)

        return withContext(Dispatchers.IO){

            try {
                val resultData = response.await()
                return@withContext combineWrapperClass(resultData,"message")
            }catch (e: Exception){
                return@withContext failureResponse?.let { e.message?.let { it1 -> WrapperClass(it, it1) } }
            }
        }
    }

    suspend fun getFiveDaysThreeHrsResponse(): WrapperClassFiveDayThreeHrs?{
        val response = ApiService.restApiService.getWeatherReportFiveDayThreeHrsReport(latitude, longtitude,API_KEY)
        return withContext(Dispatchers.IO){
            try {
                val resultData = response.await()
                return@withContext combineWrapperFiveThreeClass(resultData,"message")
            }catch (e: Exception){
                return@withContext failureResponseFiveDayThreeHrs?.let { e.message?.let { it1 -> WrapperClassFiveDayThreeHrs(it, it1) } }
            }
        }
    }

}