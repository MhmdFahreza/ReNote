package com.piggybank.renote.ui.catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatatanViewModel : ViewModel() {

    private val _catatanList = MutableLiveData<List<Catatan>>(emptyList())
    val catatanList: LiveData<List<Catatan>> = _catatanList

    private val _totalPemasukan = MutableLiveData(0.0)
    val totalPemasukan: LiveData<Double> = _totalPemasukan

    private val _totalPengeluaran = MutableLiveData(0.0)
    val totalPengeluaran: LiveData<Double> = _totalPengeluaran

    private val _totalSaldo = MutableLiveData(0.0)
    val totalSaldo: LiveData<Double> = _totalSaldo

    var selectedCatatan: Catatan? = null

    fun addCatatan(kategori: String, nominal: String, deskripsi: String) {
        val nominalValue = nominal.replace("[^\\d.-]".toRegex(), "").toDoubleOrNull() ?: 0.0
        val isPengeluaran = nominalValue < 0

        if (isPengeluaran) {
            _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) + nominalValue
        } else {
            _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) + nominalValue
        }
        _totalSaldo.value = (_totalSaldo.value ?: 0.0) + nominalValue

        val currentList = _catatanList.value ?: emptyList()
        val updatedList = currentList + Catatan(kategori, nominal, deskripsi)
        _catatanList.value = updatedList
    }

    fun updateCatatan(updatedCatatan: Catatan) {
        val updatedList = _catatanList.value?.map {
            if (it == selectedCatatan) updatedCatatan else it
        } ?: emptyList()
        _catatanList.value = updatedList
        clearSelectedCatatan()
    }

    fun deleteCatatan(catatan: Catatan) {
        _catatanList.value = _catatanList.value?.filter { it != catatan }
    }

    fun clearSelectedCatatan() {
        selectedCatatan = null
    }
}
