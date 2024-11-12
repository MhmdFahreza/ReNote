package com.piggybank.renote.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LaporanViewModel : ViewModel() {

    // LiveData for category list
    private val _categoryList = MutableLiveData<List<Laporan>>().apply {
        value = listOf(
            Laporan("Makanan", "40%"),
            Laporan("Transportasi", "30%"),
            Laporan("Hiburan", "20%"),
            Laporan("Pendidikan", "10%")
        )
    }
    val categoryList: LiveData<List<Laporan>> = _categoryList
}
