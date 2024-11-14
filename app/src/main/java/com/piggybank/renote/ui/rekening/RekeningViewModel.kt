package com.piggybank.renote.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

class RekeningViewModel : ViewModel() {

    private val _rekeningList = MutableLiveData<List<Rekening>>().apply {
        value = listOf(
            Rekening("Dana", 0L),
            Rekening("OVO", 0L),
            Rekening("BRI", 0L)
        )
    }
    val rekeningList: LiveData<List<Rekening>> = _rekeningList

    private val _totalSaldo = MutableLiveData<Long>().apply {
        value = _rekeningList.value?.sumOf { it.uang } ?: 0L
    }
    val totalSaldo: LiveData<Long> = _totalSaldo

    // Fungsi untuk menambah rekening baru
    fun addRekening(rekening: Rekening) {
        val updatedList = _rekeningList.value?.toMutableList() ?: mutableListOf()
        updatedList.add(rekening)
        _rekeningList.value = updatedList
        updateTotalSaldo()
    }

    // Fungsi untuk mengupdate total saldo
    private fun updateTotalSaldo() {
        _totalSaldo.value = _rekeningList.value?.sumOf { it.uang } ?: 0L
    }

    // Fungsi untuk format saldo menjadi mata uang Indonesia
    fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount).replace("Rp", "Rp.") // Menambahkan titik setelah "Rp"
    }
}
