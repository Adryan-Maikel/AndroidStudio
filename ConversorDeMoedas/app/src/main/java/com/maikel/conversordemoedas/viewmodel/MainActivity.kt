package com.maikel.conversordemoedas.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.maikel.conversordemoedas.R
import com.maikel.conversordemoedas.databinding.ActivityMainBinding
import com.maikel.conversordemoedas.model.IObserver
import com.maikel.conversordemoedas.model.Price
import com.maikel.conversordemoedas.repository.RateAPI

class MainActivity : AppCompatActivity(), IObserver {
    private lateinit var binding: ActivityMainBinding
    private var dollarPrice: Price = Price()
    private var euroPrice: Price = Price()
    private lateinit var alert: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        alert = AlertDialog.Builder(this).create()
        alert.setTitle("Aguarde...")
        alert.setMessage("Convertendo")

        binding.lifecycleOwner = this
        binding.dollarPrice = dollarPrice
        binding.euroPrice = euroPrice



        binding.btnConvert.setOnClickListener {
            alert.show()
            val rateAPI = RateAPI()
            rateAPI.getCurrency(applicationContext, this)
        }
    }

    override fun updateUI(data: MutableMap<String, Double>) {
        val realValue = binding.edtReal.editText?.text.toString().toDouble()
        dollarPrice.setPrice(realValue / data["dollar"]!!)
        euroPrice.setPrice(realValue / data["euro"]!!)
        alert.dismiss()
    }
}