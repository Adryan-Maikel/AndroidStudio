package com.maikel.conversordemoedas.model

interface IObserver {
    fun updateUI(data:MutableMap<String,Double>)
}