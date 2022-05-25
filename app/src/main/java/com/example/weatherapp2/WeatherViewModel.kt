package com.example.weatherapp2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp2.CurrentCityName.CurrentCityWeatherReport
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherList
import com.example.weatherapp2.five_days_three_hour_forecast.WeatherReportFiveDayThreeHour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// Data Class for WeatherData
data class WeatherData(
    var report: WeatherReport,
    var message: String
)

// Data Class for WeatherReportFiveThree
data class WeatherReportFiveThree(
    var response: WeatherReportFiveDayThreeHour,
    var message: String
)

// Data Class for the
data class WeatherReportCurrentCity(
    var response: CurrentCityWeatherReport,
    var message: String
)

class WeatherViewModel(): ViewModel() {

//  Coroutine Job
    private val job = Job()

//  Coroutine Scope
    private val scope = CoroutineScope(Dispatchers.Main + job)

//  Initialization and Declaration of Repository
    val repository = WeatherRepository()

//  Temperature State [Celsius/Fahrenheit]
    var temperatureState = MutableLiveData<String>()

//  Default Empty Weather Report
    var data: WeatherReport ?= null

//  Default Empty Weather response based on the Current City
    var emptyResponseCurrentCity: WeatherReportCurrentCity ?= null

//  Default Empty Hourly List
    var datahourly: List<Hourly> ?= null

//  Default Empty Daily List
    var dataDaily: List<Daily> ?= null

//  Default Empty WeatherList List
    var dataWeatherList: List<WeatherList> ?= null

//  Declaration of MutableLiveData Current WeatherData
    var reponse = MutableLiveData<WeatherData>()

//  Declaration of MutableLiveData Current Weather Data based on the CityName
    var currentCityWeatherResponse = MutableLiveData<WeatherReportCurrentCity>()

//  Declaration of MutableLiveData Hourly List
    var responseListHourly = MutableLiveData<List<Hourly>>()

//  Declaration of MutableLiveData Daily List
    var responseListDaily = MutableLiveData<List<Daily>>()

//  Declaration of MutableLiveData WeatherList list
    var fiveDayThreeHrs = MutableLiveData<List<WeatherList>>()

//  Declaration of MutableLiveData for latitude
    var latitude = MutableLiveData<String>()

//  Declaration of MutableLiveData for longitude
    var longitude = MutableLiveData<String>()

//  Declaration of MutableLiveData for CityName
    var cityName = MutableLiveData<String>()


    init {
        reponse.value = data?.let { WeatherData(it,"") }

//      Default Initialization of fiveDayThreeHrs to null List [dataWeatherList]
        fiveDayThreeHrs.value = dataWeatherList

//      Default Initialization of responseListHourly to null List [datahourly]
        responseListHourly.value = datahourly

//      Default Initialization of responseListDaily to null List [dataDaily]
        responseListDaily.value = dataDaily

//      Default Initialization of the current City Weather Data
        currentCityWeatherResponse.value = emptyResponseCurrentCity?.let { WeatherReportCurrentCity(it.response, "") }

//      Default Initialization of Temperature State is Celsius
        temperatureState.value = "Celsius"

//      Initialization of the MutableLiveData for the latitude
        latitude.value = "12.831463782688502"

//      Initialization of the MutableLiveData for the longitude
        longitude.value = "80.04948506013582"

//      Initialization of the MutableLiveData for the cityName
        cityName.value = "Guduvancheri"

//      Default function Call to get the current city Weather data & Hourly & Daily  from the open Weather api, when the viewModel is created.
        getWeatherResponseBasedOnCityName(cityName.value.toString())
    }

//  Function Declaration for the getting the data for city Name
    fun getWeatherResponseBasedOnCityName(cityName: String){
        scope.launch {
            val weatherResponseBasedOnCityName = repository.getCurrentCityName(cityName)
            if (weatherResponseBasedOnCityName != null){
                val temp = WeatherReportCurrentCity(weatherResponseBasedOnCityName.data, weatherResponseBasedOnCityName.message)
                currentCityWeatherResponse.value = temp
                latitude.value = temp.response.coord.lat.toString()
                longitude.value = temp.response.coord.lon.toString()
                getWeatherResponse()
                getFiveDaysThreeHrsWeatherResponse()
            }
        }
    }

//  Function Declaration for getting the data for Current Weather Data , Hourly, Daily
    fun getWeatherResponse(){
        scope.launch {
            val report = repository.getWeatherResponse(latitude.value.toString(), longitude.value.toString())

            if (report != null){
                val temp = WeatherData(report.data, report.message)
                reponse.value = temp
                responseListHourly.value = temp.report.hourly
                responseListDaily.value = temp.report.daily
            }
        }
    }

    fun getFiveDaysThreeHrsWeatherResponse(){
        scope.launch {
            val report = repository.getFiveDaysThreeHrsResponse(latitude.value.toString(), longitude.value.toString())

            if (report != null){
                val temp = WeatherReportFiveThree(report.data, report.message)
                fiveDayThreeHrs.value = temp.response.list

            }
        }
    }
}