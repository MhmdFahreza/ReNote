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

    fun editCatatan(newNominal: String, newDeskripsi: String) {
        selectedCatatan?.let { catatan ->
            val currentList = _catatanList.value?.toMutableList() ?: mutableListOf()
            val index = currentList.indexOf(catatan)
            if (index != -1) {
                // Update totals
                val oldNominalValue = catatan.nominal.replace("[^\\d.-]".toRegex(), "").toDoubleOrNull() ?: 0.0
                val newNominalValue = newNominal.replace("[^\\d.-]".toRegex(), "").toDoubleOrNull() ?: 0.0

                if (oldNominalValue < 0) {
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) - oldNominalValue
                } else {
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) - oldNominalValue
                }

                if (newNominalValue < 0) {
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) + newNominalValue
                } else {
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) + newNominalValue
                }
                _totalSaldo.value = (_totalSaldo.value ?: 0.0) - oldNominalValue + newNominalValue

                // Update catatan
                val updatedCatatan = catatan.copy(nominal = newNominal, deskripsi = newDeskripsi)
                currentList[index] = updatedCatatan
                _catatanList.value = currentList

                // Update selectedCatatan
                selectedCatatan = updatedCatatan
            }
        }
    }

    fun deleteSelectedCatatan() {
        selectedCatatan?.let { catatan ->
            val currentList = _catatanList.value?.toMutableList() ?: mutableListOf()
            if (currentList.remove(catatan)) {
                // Update totals
                val nominalValue = catatan.nominal.replace("[^\\d.-]".toRegex(), "").toDoubleOrNull() ?: 0.0
                if (nominalValue < 0) {
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) - nominalValue
                } else {
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) - nominalValue
                }
                _totalSaldo.value = (_totalSaldo.value ?: 0.0) - nominalValue

                // Update list
                _catatanList.value = currentList
                selectedCatatan = null
            }
        }
    }

    fun clearSelectedCatatan() {
        selectedCatatan = null
    }
}
