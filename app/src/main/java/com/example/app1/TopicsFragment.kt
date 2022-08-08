package com.example.app1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.app1.model.FeederPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class TopicsFragment : Fragment() {
    private var checkboxes = ArrayList<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun saveTopics() {
        if (activity != null) {
            val pref = activity?.getSharedPreferences(
                getString(R.string.topics), Context.MODE_PRIVATE
            )

            val indexes: MutableList<Int> = ArrayList()
            for (i in 0 until checkboxes.size){
                if (checkboxes[i].isChecked){
                    indexes.add(i)
                }
            }

            if (indexes.isNotEmpty()){
                if (pref != null) {
                    val feederPreferences = context?.let { FeederPreferences(it) }
                    feederPreferences?.setFavouriteTopics(indexes)
                }
            } else {
                Toast.makeText(context, R.string.title_topics, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.lang), Context.MODE_PRIVATE
        ) ?: return
        val defaultValue = resources.getStringArray(R.array.langs)[0]
        val langString = sharedPref.getString(getString(R.string.lang), defaultValue)

        val topics = when (langString?.split(':')?.get(0)) {
            "it" -> {
                resources.getStringArray(R.array.topics_it)
            }
            "en" -> {
                resources.getStringArray(R.array.topics_en)
            }
            "fr" -> {
                resources.getStringArray(R.array.topics_fr)
            }
            "de" -> {
                resources.getStringArray(R.array.topics_de)
            }
            else -> {
                resources.getStringArray(R.array.topics_it)
            }
        }
        val list = activity?.findViewById<LinearLayout>(R.id.layout_topics)

        for (i in 0 until topics.size) {
            checkboxes.add(CheckBox(activity))
            checkboxes[i].setText(topics[i].toString())
            list?.addView(checkboxes[i])
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
