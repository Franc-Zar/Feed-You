package com.example.app1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LangFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var rgLangs = activity?.findViewById<RadioGroup>(R.id.rg_langs)

        var content: String? = ""
        try {
            val inputStream: InputStream = activity?.assets!!.open("langs.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            content = String(buffer)

            var json = JSONObject(content).getJSONArray("langs")
            for (i in 0 until json.length()) {
                var radio_button = RadioButton(activity)
                radio_button.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                radio_button.setText(json.getString(i))
                radio_button.id = i
                rgLangs!!.addView(radio_button)

            }
            rgLangs!!.setOnCheckedChangeListener { group, checkedId ->
                val checkedRadioButtonId: Int = rgLangs.checkedRadioButtonId
                val radioBtn = activity?.findViewById(checkedRadioButtonId) as RadioButton
                Toast.makeText(activity, radioBtn.text, Toast.LENGTH_SHORT).show()
            }

            Log.d("PAOLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", json.toString())
            Log.d("OOOOOOOOOOOUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU", rgLangs.childCount.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lang, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LangFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}