package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.fireworks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.R

class FireworksFragment : Fragment() {

    companion object {
        fun newInstance() = FireworksFragment()
    }

    private lateinit var viewModel: FireworksViewModel
    private lateinit var drawView: FireworksDrawView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_fireworks, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(FireworksViewModel::class.java)
        drawView = view.findViewById(R.id.fireworksDrawView)
        drawView.viewModel = viewModel

        val pointsObserver: Observer<ArrayList<FireworksViewModel.Point>>
                = Observer<ArrayList<FireworksViewModel.Point>> {
            Log.i("FireworksFragment", "Extra Credit: <points> Updated")
            drawView.invalidate()
        }
        viewModel.points.observe(viewLifecycleOwner, pointsObserver)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.startAnimation()
    }
}