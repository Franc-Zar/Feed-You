package com.example.app1.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.app1.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import kotlin.random.Random

class FeederPreferences (private val context: Context){
    private var preferences =  mutableListOf<Double>()
    private var boundaries =  mutableListOf<Double>()
    private val pref : SharedPreferences

    init{
        pref = context.getSharedPreferences(context.getString(R.string.prefTopics), Context.MODE_PRIVATE)
        val values = pref.all[context.getString(R.string.prefTopics)]
        val json = if (values != "null" && values != null) JSONArray(values.toString()) else JSONArray("[]")


        if(json.length() != 0) {
            val indexes = mutableListOf<Int>()
            for (i in 0 until context.resources.getStringArray(R.array.topics_it).size) {
                if (i<json.length()) {
                    preferences.add(json.getDouble(i))
                }else{
                    preferences.add(0.0)
                }
                boundaries.add(0.0)
            }
            setBounds()
        }
        else{
            for (i in 0 until context.resources.getStringArray(R.array.topics_it).size) {
                preferences.add(0.0)
                boundaries.add(0.0)
            }
        }
    }

    //Per adesso la logica è molto semplice, ma può ovviamente essere cambiata
    fun setFavouriteTopics(indexes: MutableList<Int>) {
        val numTopics = context.resources.getStringArray(R.array.topics_it).size
        val inTopic = when {
            indexes.isEmpty() -> {0.0}
            indexes.size == numTopics -> {(1.0 / indexes.size)}
            else -> {(0.8 / indexes.size)}
        }
        val offTopic = when {
            indexes.isEmpty() -> {(1.0 / (numTopics - indexes.size))}
            indexes.size == numTopics -> {0.0}
            else -> {(0.2 / (numTopics - indexes.size))}
        }
        for (i in 0 until numTopics) {
            preferences[i] = (if (i in indexes) inTopic else offTopic)
        }
        savePreferences()
        //setBounds()
    }


    fun update(topic : Int){
        if (topic == -1){
            return
        }
        for (i in 0 until preferences.size){
            if (i==topic){
                preferences[topic] = ((preferences[topic] + 0.01)/1.01)
            }else {
                preferences[i] = (preferences[i] / 1.01)
            }
        }
        savePreferences()
    }

    fun savePreferences(){
        val save = context.getSharedPreferences(context.getString(R.string.prefTopics), Context.MODE_PRIVATE)
        val current_user = Firebase.auth.currentUser!!
        if (! current_user.isAnonymous){
            Firebase.database.reference.child(context.getString(R.string.firebase_users))
                .child(current_user.uid).child("topics").setValue(preferences)
        }
        with(save.edit()){
            putString( context.getString(R.string.prefTopics), preferences.toString())
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
        if (i >= boundaries.size){
            Log.d("BOUND", "Boundaries sbagliati")
            return boundaries.size -1
        }
        return i
    }
}