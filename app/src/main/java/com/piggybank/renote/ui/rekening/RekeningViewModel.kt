package com.piggybank.renote.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RekeningViewModel : ViewModel() {

    private val _rekeningList = MutableLiveData<List<Rekening>>().apply {
        value = listOf(
            Rekening("Dana", 0.0),
            Rekening("OVO", 0.0),
            Rekening("BRI", 0.0)
        )
    }
    val rekeningList: LiveData<List<Rekening>> = _rekeningList

    private val _totalSaldo = MutableLiveData<Double>().apply {
        value = _rekeningList.value?.sumOf { it.balance } ?: 0.0
    }
    val totalSaldo: LiveData<Double> = _totalSaldo

    // Fungsi untuk menambah rekening baru
    fun addRekening(rekening: Rekening) {
        val updatedList = _rekeningList.value?.toMutableList() ?: mutableListOf()
        updatedList.add(rekening)
        _rekeningList.value = updatedList
        updateTotalSaldo()
    }

    // Fungsi untuk mengupdate total saldo
    private fun updateTotalSaldo() {
        _totalSaldo.value = _rekeningList.value?.sumOf { it.balance } ?: 0.0
    }
}
