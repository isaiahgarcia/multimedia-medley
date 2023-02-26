package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoViewModel : ViewModel() {
    // store video position
    var videoPos: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 0
    }
}