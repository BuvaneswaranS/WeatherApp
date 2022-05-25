package com.example.weatherapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class CityNameDialogFragment(weatherViewModel: WeatherViewModel): DialogFragment() {

    val viewModel: WeatherViewModel =  weatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fragmentView: View = inflater.inflate(R.layout.dialog_fragment_city_name,container, false)

        val inputData = fragmentView.findViewById<EditText>(R.id.cityNameDialogFragment_input_data)
        val cancelButton = fragmentView.findViewById<Button>(R.id.cityNameDialogFragment_cancel_button)
        val changeButton = fragmentView.findViewById<Button>(R.id.cityNameDialogFragment_change_button)

        inputData.setText(viewModel.cityName.value)


        cancelButton.setOnClickListener {
            dismiss()
        }

        changeButton.setOnClickListener {
            if (inputData.text.isEmpty()){

            }else if (inputData.text.isNotEmpty()){
                viewModel.cityName.value = inputData.text.toString()
            }

            dismiss()
        }

        return fragmentView
    }
}