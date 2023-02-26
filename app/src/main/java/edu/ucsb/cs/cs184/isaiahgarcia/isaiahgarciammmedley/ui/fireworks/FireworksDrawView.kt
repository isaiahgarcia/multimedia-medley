package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.fireworks

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.max

class FireworksDrawView : View {
    constructor(context: Context) : super(context) { initializeView() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initializeView() }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int)
            : super(context, attrs, defStyle) { initializeView() }

    var viewModel: FireworksViewModel? = null
    var paint: Paint = Paint()

    private fun initializeView() {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            viewModel?.makePoints(event.x, event.y)
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val dim: Int = max(width, height) + 5
        viewModel!!.points.value!!.forEach { point: FireworksViewModel.Point ->
            if (point.x > -5 && point.x < dim && point.y > -5 && point.y < dim)
                canvas.drawCircle(point.x, point.y, 10.0F, paint)
            else
                point.visible = false
        }
    }

}