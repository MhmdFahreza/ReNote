package com.piggybank.renote.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LaporanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Laporan Fragment"
    }
    val text: LiveData<String> = _text
}