package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to the Multimedia Medley!\n Pick a demo from the top-left menu"
    }
    val text: LiveData<String> = _text
}