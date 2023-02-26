package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.imagescaling

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.R
import kotlin.random.Random


class ImageScalingFragment : Fragment() {

    companion object {
        fun newInstance() = ImageScalingFragment()
    }

    private lateinit var viewModel: ImageScalingViewModel
    private lateinit var scaleDetect: ScaleGestureDetector
    private lateinit var imageView: ImageView
    private lateinit var fab: FloatingActionButton

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_imagescaling, container, false)
        view.setOnTouchListener { v, event ->
            scaleDetect.onTouchEvent(event)
            true
        }

        viewModel = ViewModelProvider(requireActivity()).get(ImageScalingViewModel::class.java)
        scaleDetect = ScaleGestureDetector(requireActivity(), ScaleGestureListener())
        imageView = view.findViewById(R.id.imageScaleView)
        fab = view.findViewById(R.id.imageScaleFab)
        fab.setOnClickListener { view ->
            viewModel.imageID.value = Random.nextInt(0, 1084)
        }

        val imageIDObserver: Observer<Int> = Observer { value: Int ->
            loadImage()
            Log.i("ImageScalingFragment", "Extra Credit: <imageID> Updated")
        }
        viewModel.imageID.observe(viewLifecycleOwner, imageIDObserver)

        val scaleFactorObserver: Observer<Float> = Observer { value: Float ->
            imageView.scaleX = viewModel.scaleFactor.value!!
            imageView.scaleY = viewModel.scaleFactor.value!!
            Log.i("ImageScalingFragment", "Extra Credit: <scaleFactor> Updated")
        }
        viewModel.scaleFactor.observe(viewLifecycleOwner, scaleFactorObserver)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadImage()
    }

    private fun loadImage(){
        val url: String = "https://picsum.photos/400/600".format(viewModel.imageID.value)

        Glide.with(this)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.image_scale_default)
            .into(imageView)

        imageView.scaleX = viewModel.scaleFactor.value!!
        imageView.scaleY = viewModel.scaleFactor.value!!
    }

    inner class ScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            viewModel.scaleFactor.value = viewModel.scaleFactor.value!! * detector.scaleFactor
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {}
    }
}