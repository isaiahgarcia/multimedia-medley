package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.imagescaling

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ImageScalingViewModel : ViewModel() {
    var imageID: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = Random.nextInt(0, 1085)
    }

    var scaleFactor: MutableLiveData<Float> = MutableLiveData<Float>().apply {
        value = 1.0F
    }

}