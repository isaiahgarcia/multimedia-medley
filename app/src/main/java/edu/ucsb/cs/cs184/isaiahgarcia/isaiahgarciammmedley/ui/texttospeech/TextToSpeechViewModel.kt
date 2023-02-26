package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.texttospeech

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextToSpeechViewModel : ViewModel() {
    // store string value in edit text view
    val input: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }
}