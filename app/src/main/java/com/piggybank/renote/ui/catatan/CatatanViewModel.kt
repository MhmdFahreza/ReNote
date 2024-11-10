package com.piggybank.renote.ui.catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatatanViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = " Catatan Fragment "
    }
    val text: LiveData<String> = _text
}
