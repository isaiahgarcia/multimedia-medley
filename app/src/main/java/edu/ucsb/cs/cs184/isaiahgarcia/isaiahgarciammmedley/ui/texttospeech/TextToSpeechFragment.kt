package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.texttospeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.R
import java.util.*

class TextToSpeechFragment : Fragment() {

    companion object {
        fun newInstance() = TextToSpeechFragment()
    }

    private lateinit var viewModel: TextToSpeechViewModel
    private lateinit var editText: EditText
    private lateinit var tts: TextToSpeech
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_texttospeech, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(TextToSpeechViewModel::class.java)
        editText = view.findViewById(R.id.ttsEditText)
        editText.addTextChangedListener(InputTextWatcher())
        tts = TextToSpeech(context, TextToSpeech.OnInitListener { status: Int ->
            tts.language = Locale.US })
        fab = view.findViewById(R.id.ttsFab)
        fab.setOnClickListener {
            tts.speak(editText.text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        val inputObserver: Observer<String> = Observer<String> { value: String ->
            Log.i("TextToSpeechFragment", "Extra Credit: <input> Updated")
        }
        viewModel.input.observe(viewLifecycleOwner, inputObserver)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editText.setText(viewModel.input.value)
    }

    inner class InputTextWatcher: TextWatcher {
        override fun afterTextChanged(s: Editable) {
            viewModel.input.value = editText.text.toString()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}