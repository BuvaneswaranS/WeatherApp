package com.example.weatherapp2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherList
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherReportFiveDayThreeHour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


data class WeatherData(
    var report: WeatherReport,
    var message: String
)

data class WeatherReportFiveThree(
    var response: WeatherReportFiveDayThreeHour,
    var message: String
)

class WeatherViewModel(): ViewModel() {

    private val job = Job()

    private val scope = CoroutineScope(Dispatchers.Main + job)

    val repository = WeatherRepository()

    var data: WeatherReport ?= null

    var datahourly: List<Hourly> ?= null
    var dataDaily: List<Daily> ?= null
    var dataWeatherList: List<WeatherList> ?= null

    var fiveDaysThreeHour: WeatherReportFiveDayThreeHour ?= null

    var responseListNull: List<WeatherReport> ?= null

    var reponse = MutableLiveData<WeatherData>()

    var responseListHourly = MutableLiveData<List<Hourly>>()

    var responseListDaily = MutableLiveData<List<Daily>>()

    var fiveDayThreeHrs = MutableLiveData<List<WeatherList>>()

    var temperatureState = MutableLiveData<String>()

    init {
        getWeatherReponse()
        reponse.value = data?.let { WeatherData(it,"") }
        fiveDayThreeHrs.value = dataWeatherList
        responseListHourly.value = datahourly
        responseListDaily.value = dataDaily
        temperatureState.value = "Celsius"
    }

    fun getWeatherReponse(){
        scope.launch {
            val report = repository.getWeatherResponse()

            if (report != null){
                val temp = WeatherData(report.data, report.message)
                reponse.value = temp
                responseListHourly.value = temp.report.hourly
                responseListDaily.value = temp.report.daily
                Log.i("TestingApp","Data --> ${temp.report.current.feelsLike}")
                Log.i("TestingApp","Test 1 --> ${reponse.value!!.report.lat}")
            }
        }
    }

    fun getFiveDaysThreeHrsWeatherResponse(){
        scope.launch {
            val report = repository.getFiveDaysThreeHrsResponse()

            if (report != null){
                val temp = WeatherReportFiveThree(report.data, report.message)
                fiveDayThreeHrs.value = temp.response.list
                Log.i("TestingApp","Data 1 --> ${temp.response.city}")
            }
        }
    }

}