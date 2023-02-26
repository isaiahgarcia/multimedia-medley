package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.speechtotext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpeechToTextViewModel : ViewModel() {
    var result: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "Tap to speak"
    }
}