package com.example.app1.model

import android.content.Context
import android.content.SharedPreferences
import com.example.app1.R
import org.json.JSONArray
import kotlin.random.Random

class FeederPreferences (private val context: Context){
    private var preferences =  mutableListOf<Double>()
    private var boundaries =  mutableListOf<Double>()
    private val pref : SharedPreferences

    init{
        pref = context.getSharedPreferences(context.getString(R.string.prefTopics), Context.MODE_PRIVATE)
        val values = pref.all[context.getString(R.string.prefTopics)]
        val json = if (values != null) JSONArray(values.toString()) else JSONArray("[]")

        if(json.length() != 0) {
            for (i in 0 until json.length()) {
                preferences.add(json.get(i) as Double)
                boundaries.add(0.0)
                setBounds()
            }
        }else {
            for (i in 0 until context.resources.getStringArray(R.array.topics_it).size) {
                preferences.add(0.0)
                boundaries.add(0.0)
            }
        }

    }

    //Per adesso la logica è molto semplice, ma può ovviamente essere cambiata
    fun setFavouriteTopics(indexes: MutableList<Int>) {
        val inTopic = (0.85 / indexes.size)
        val offTopic = (0.15 / (preferences.size - indexes.size))
        for (i in 0 until preferences.size) {
            preferences[i] = if (i in indexes) inTopic else offTopic
        }
        savePreferences()
        setBounds()
    }


    fun update(topic : Int){
        for (i in 0 until preferences.size){
            if (i==topic){
                preferences[topic] = ((preferences[topic] + 0.01)/1.01)
            }else {
                preferences[topic] = (preferences[topic] / 1.01)
            }
        }
        savePreferences()
    }

    fun savePreferences(){
        with(pref.edit()){
            putString(context.getString(R.string.prefTopics), preferences.toString())
            apply()
        }
        setBounds()
    }

    fun setBounds(){
        boundaries[0] = preferences[0]
        for (i in 1 until preferences.size){
            boundaries[i] = 0.0
            var j = 0
            while (j<=i){
                boundaries[i] = boundaries[i] + preferences[j]
                j++
            }
        }
    }

    fun getRandTopic(): Int {
        val rand= Random.nextDouble()
        var i =0
        while(rand > boundaries[i]){
            i++
            if (i>=boundaries.size){break}
        }
        return i
    }
}