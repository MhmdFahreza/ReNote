package com.piggybank.renote.ui.catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatatanViewModel : ViewModel() {

    private val _catatanList = MutableLiveData<List<Catatan>>().apply {
        value = listOf(
            Catatan("Pemasukan", "Deskripsi pemasukan 1", 500000.0),
            Catatan("Pengeluaran", "Deskripsi pengeluaran 1", -200000.0),
            Catatan("Pemasukan", "Deskripsi pemasukan 2", 300000.0),
            Catatan("Pengeluaran", "Deskripsi pengeluaran 1", -200000.0),
            Catatan("Pemasukan", "Deskripsi pemasukan 2", 300000.0)
        )
    }
    val catatanList: LiveData<List<Catatan>> = _catatanList
}
