package com.piggybank.renote.ui.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RekeningViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Rekening Fragment"
    }
    val text: LiveData<String> = _text
}