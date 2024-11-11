package com.piggybank.renote.ui.catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatatanViewModel : ViewModel() {

    private val _catatanList = MutableLiveData<List<Catatan>>().apply {
        value = listOf(
            Catatan("Makanan", "Deskripsi pemasukan 1", 500000.0),
        )
    }
    val catatanList: LiveData<List<Catatan>> = _catatanList
}
