package com.piggybank.renote.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RekeningViewModel : ViewModel() {

    private val _rekeningList = MutableLiveData<List<Rekening>>().apply {
        value = listOf(
            Rekening("Dana", 1000000.0),
            Rekening("OVO", 2500000.0),
            Rekening("BRI", 1500000.0)
        )
    }
    val rekeningList: LiveData<List<Rekening>> = _rekeningList

    private val _totalSaldo = MutableLiveData<Double>().apply {
        value = _rekeningList.value?.sumOf { it.balance } ?: 0.0
    }
    val totalSaldo: LiveData<Double> = _totalSaldo
}
