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

    fun updateCatatanWithAmountUpdate(updatedCatatan: Catatan, oldCatatan: Catatan?) {
        oldCatatan?.let {
            val oldNominal = getNominalValue(it.nominal)
            val newNominal = getNominalValue(updatedCatatan.nominal)

            if (it.kategori == updatedCatatan.kategori) {
                val difference = newNominal - oldNominal
                if (it.kategori == "Pemasukan") {
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) + difference
                } else {
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) + difference
                }
            } else {
                if (it.kategori == "Pemasukan") {
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) - oldNominal
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) + newNominal
                } else {
                    _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) - oldNominal
                    _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) + newNominal
                }
            }

            // Update saldo
            _totalSaldo.value = (_totalSaldo.value ?: 0.0) - oldNominal + newNominal
        }

        // Update the list and clear selection
        val updatedList = _catatanList.value?.map { if (it == oldCatatan) updatedCatatan else it } ?: emptyList()
        _catatanList.value = updatedList
        clearSelectedCatatan()
    }

    fun deleteCatatan(catatan: Catatan) {
        val currentList = _catatanList.value?.filter { it != catatan } ?: emptyList()
        _catatanList.value = currentList

        val nominalValue = getNominalValue(catatan.nominal)
        if (catatan.kategori == "Pemasukan") {
            _totalPemasukan.value = (_totalPemasukan.value ?: 0.0) - nominalValue
        } else {
            _totalPengeluaran.value = (_totalPengeluaran.value ?: 0.0) - nominalValue
        }
        _totalSaldo.value = (_totalSaldo.value ?: 0.0) - nominalValue
    }

    private fun getNominalValue(nominal: String): Double {
        return nominal.replace("[^\\d.-]".toRegex(), "").toDoubleOrNull() ?: 0.0
    }

    fun clearSelectedCatatan() {
        selectedCatatan = null
    }
}
