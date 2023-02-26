package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.speechtotext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.R
import java.util.*


class SpeechToTextFragment : Fragment() {

    companion object {
        fun newInstance() = SpeechToTextFragment()
    }

    private lateinit var viewModel: SpeechToTextViewModel
    private lateinit var textView: TextView
    private lateinit var fab: FloatingActionButton
    private var request: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_speechtotext, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SpeechToTextViewModel::class.java)
        textView = view.findViewById(R.id.sttTextView)
        fab = view.findViewById(R.id.sttFab)
        fab.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tap to speak")

            try {
                startActivityForResult(intent, request)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
            }
        }

        val resultObserver: Observer<String> = Observer<String> { value: String ->
            textView.text = viewModel.result.value!!
            Log.i("SpeechToTextFragment", "Extra Credit: <result> Updated")
        }
        viewModel.result.observe(viewLifecycleOwner, resultObserver)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textView.text = viewModel.result.value
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == request && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.result.value = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
        }
    }

}