package com.example.weatherapp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var hourlyAdapter: WeatherDataAdapter
    private lateinit var dailyAdapter: WeatherDailyAdapter
    private lateinit var fiveDaysThreeHrsAdapter: WeatherFiveDThreeHrsAdapter

    private lateinit var viewModel: WeatherViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val showCurrentWeather = findViewById<Button>(R.id.show_current_weather_button)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

       val hourlyRecyclerView = findViewById<RecyclerView>(R.id.weather_hourly_forecast_recyclerView)
       val dailyRecyclerView = findViewById<RecyclerView>(R.id.weather_seven_days_weather_forecast_recyclerView)
       val fiveDaysThreeHrsRecyclerView = findViewById<RecyclerView>(R.id.weather_five_days_three_hour_forecast_recyclerView)



        viewModel.getWeatherReponse()
        viewModel.getFiveDaysThreeHrsWeatherResponse()

        viewModel.temperatureState.observe(this, Observer {state ->

            hourlyAdapter = WeatherDataAdapter(viewModel)
            dailyAdapter = WeatherDailyAdapter(viewModel)
            fiveDaysThreeHrsAdapter = WeatherFiveDThreeHrsAdapter(viewModel)

            hourlyRecyclerView.adapter = hourlyAdapter
            dailyRecyclerView.adapter = dailyAdapter
            fiveDaysThreeHrsRecyclerView.adapter = fiveDaysThreeHrsAdapter

            hourlyRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            dailyRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            fiveDaysThreeHrsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

            if (state == "Celsius"){
                viewModel.responseListHourly.observe(this, Observer {list ->
                    hourlyAdapter.submitList(list)

                })

                viewModel.responseListDaily.observe(this, Observer {list ->
                    dailyAdapter.submitList(list)
                })

                viewModel.fiveDayThreeHrs.observe(this, Observer {response ->
                    if (response == null){
                        Log.i("TestingApp","Response is null")
                    }else{
                        fiveDaysThreeHrsAdapter.submitList(response)
                    }

                })


            }else if(state == "Fahrenheit"){
                viewModel.responseListHourly.observe(this, Observer {list ->
                    hourlyAdapter.submitList(list)

                })

                viewModel.responseListDaily.observe(this, Observer {list ->
                    dailyAdapter.submitList(list)
                })

                viewModel.fiveDayThreeHrs.observe(this, Observer {response ->
                    if (response == null){
                        Log.i("TestingApp","Response is null")
                    }else{
                        fiveDaysThreeHrsAdapter.submitList(response)
                    }

                })


            }
        })

//        viewModel.responseListHourly.observe(this, Observer {list ->
//            hourlyAdapter.submitList(list)
//
//        })
//
//        viewModel.responseListDaily.observe(this, Observer {list ->
//            dailyAdapter.submitList(list)
//        })
//
//        viewModel.fiveDayThreeHrs.observe(this, Observer {response ->
//            if (response == null){
//                Log.i("TestingApp","Response is null")
//            }else{
//                fiveDaysThreeHrsAdapter.submitList(response)
//            }
//
//        })


        showCurrentWeather.setOnClickListener {
            val dialogFragment = CurrentWeatherBottomSheet(viewModel)
            dialogFragment.show(supportFragmentManager,"dialogFragment")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val switchMenuButton: MenuItem? = menu?.findItem(R.id.temperature_change_switch_button)

        switchMenuButton?.setActionView(R.layout.item_layout_switch_button)

        val sky: SwitchCompat? = switchMenuButton?.actionView?.findViewById(R.id.temp_change_switch)
        sky?.showText = true
        sky?.textOn = ""
        sky?.textOff = ""
        sky?.text = "Celsius"
        sky?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                Log.i("TestingApp","Checked")
                sky.text = "Fahrenheit"
                viewModel.temperatureState.value = "Fahrenheit"
            }else if (!isChecked){
                Log.i("TestingApp","Not Checked")
                sky.text = "Celsius"
                viewModel.temperatureState.value = "Celsius"
            }
        }

        return true
    }

}