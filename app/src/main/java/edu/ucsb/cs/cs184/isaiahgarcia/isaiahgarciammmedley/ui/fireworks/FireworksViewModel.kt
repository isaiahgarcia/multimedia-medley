package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.fireworks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class FireworksViewModel : ViewModel() {
    private var count: Int = 10
    private val animThread = AnimationThread(PointsUpdate(), 20)
    var points: MutableLiveData<ArrayList<Point>> = MutableLiveData<ArrayList<Point>>().apply {
        value = ArrayList<Point>()
    }

    fun startAnimation() {
        animThread.start()
    }

    fun stopAnimation() {
        animThread.stop()
    }

    fun makePoints(x: Float, y: Float) {
        val temp: ArrayList<Point> = ArrayList<Point>()
        for (i in 0 until count) { temp.add(Point(x, y)) }
        points.value = temp
    }

    inner class Point(var x: Float, var y: Float) {
        var dx: Float = (Random.nextFloat() - 0.5F) * 15
        var dy: Float = (Random.nextFloat() - 0.5F) * 15
        var visible: Boolean = true

        fun next() : Point {
            this.x += this.dx
            this.y += this.dy
            return this
        }
    }

    inner class PointsUpdate: Runnable {
        override fun run() {
            val temp: ArrayList<Point> = ArrayList<Point>()
            for (i in 0 until points.value!!.size) {
                if (points.value!![i].visible)
                    temp.add(points.value!![i].next())
            }
            if (temp.size > 0) points.value = temp
        }
    }
}