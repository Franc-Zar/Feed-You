package com.example.app1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class LangFragment : Fragment() {
    fun saveLang() {
        val pref =
            activity?.getSharedPreferences(getString(R.string.lang), Context.MODE_PRIVATE)
        val language =
            activity?.findViewById<Spinner>(R.id.spinner_lang)?.selectedItem.toString()
                .split(':')[0]
        if (pref != null) {
            with(pref.edit()) {
                putString(getString(R.string.lang), language)
                apply()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lang, container, false)
    }
}