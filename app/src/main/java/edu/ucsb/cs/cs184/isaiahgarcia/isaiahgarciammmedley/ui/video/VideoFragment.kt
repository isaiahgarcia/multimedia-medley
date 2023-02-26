package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.video

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.R


class VideoFragment : Fragment() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel
    private lateinit var videoView: VideoView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_video, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)
        videoView = view.findViewById<VideoView>(R.id.videoPlayView)
        videoView.setMediaController(MediaController(context))
        videoView.setOnPreparedListener { mp ->
            mp.setOnSeekCompleteListener {
                Log.d("VideoPlayerFragment", "After Seek: %d".format(videoView.currentPosition))
                videoView.start()
            }
        }

        val videoPosObserver: Observer<Int> = Observer<Int> { value: Int ->
            Log.i("VideoPlaybackFragment", "Extra Credit: <videoPos> Updated to %d".format(viewModel.videoPos.value))
        }
        viewModel.videoPos.observe(viewLifecycleOwner, videoPosObserver)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadVideo()
    }

    private fun loadVideo() {
        val path: String = "android.resource://%s/%d".format(requireActivity().packageName, R.raw.bigbuck)

        videoView.setVideoURI(Uri.parse(path))

        if (viewModel.videoPos.value != 0) {
            Log.d("VideoPlayerFragment", "Before Seek: %d".format(videoView.currentPosition))
            Log.d("VideoPlayerFragment", "videoPos.value %d".format(viewModel.videoPos.value!!))
            videoView.seekTo(viewModel.videoPos.value!!)
        } else {
            videoView.start()
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.videoPos.value = videoView.currentPosition
    }
}