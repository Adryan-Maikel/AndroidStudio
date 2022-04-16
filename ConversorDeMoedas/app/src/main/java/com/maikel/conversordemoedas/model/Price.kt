package com.maikel.conversordemoedas.model

import androidx.databinding.ObservableDouble

class Price {
    private var price = ObservableDouble()
    fun getPrice() = this.price
    fun setPrice(value: Double) {
        price.set(value)
    }
}