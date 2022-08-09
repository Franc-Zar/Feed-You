package com.example.app1

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app1.model.FeederPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PreferenceTests {

    private lateinit var feederPreferences: FeederPreferences
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val numTopic = context.resources.getStringArray(R.array.topics_it).size
    private val sharedPref = context.getSharedPreferences(context.getString(R.string.prefTopics), Context.MODE_PRIVATE)
    @Before
    fun setup(){
        Log.d("Shared preferences: ", sharedPref.all.toString())
        with(sharedPref.edit()){
            putString( context.getString(R.string.prefTopics), "[0.4, 0.05, 0.05, 0.05, 0.05, 0.4]")
            apply()
        } //array inserito più corto, è possibile nel caso in cui si vadano ad aggiungere dei topic, senza aggiornare le preferenze
        feederPreferences = FeederPreferences(context)
    }

    // Nei test spesso si farà il check con i boundaries, perché di fatto la classe FeederPreferences va a cascata ad aggiornare tale valore,
    // passando per le preferences, che sono salvate su SharedPreferences, e, se si è loggati, in Firebase RealTime

    @Test
    fun testRandTopic() {
        var flag = true
        var i = 0
        while(flag && i<20){
            val randTopic = feederPreferences.getRandTopic()
            flag = randTopic in 0 until numTopic
            i += 1
        }
        assertTrue(flag)
    }

    @Test
    fun assertSetBounds(){
        feederPreferences.setBounds()
        var flag = true
        var i = 0
        while(flag && i<feederPreferences.boundaries.size-1){
            flag = feederPreferences.boundaries[i]<=1.0
            i+=1
        }
        assert(flag && feederPreferences.boundaries[feederPreferences.boundaries.size-1]==1.0)
    }

    @Test
    fun assertFavouriteTopics(){
        //è permesso scegliere indexes dalla lista dei topics, quindi indexes.size<=numTopics sempre
        feederPreferences.setFavouriteTopics(mutableListOf(0,1,4,6))
        var flag = true
        var i = 0
        while(flag && i<feederPreferences.boundaries.size-1){
            flag = feederPreferences.boundaries[i]<=1.0
            i+=1
        }
        assert(flag && feederPreferences.boundaries[feederPreferences.boundaries.size-1]==1.0)
    }

    @Test
    fun assertUpdated(){
        // il topic non può essere fuori range poiché l'errore è considerato precedente, e sostituito con -1
        val oldBounds = feederPreferences.boundaries
        feederPreferences.update(-1)
        assertEquals(feederPreferences.boundaries, oldBounds)
        var flag = true
        var i = 0
        while(flag && i<feederPreferences.boundaries.size-1){
            flag = feederPreferences.boundaries[i]<=1.0
            i+=1
        }
        assert(flag && feederPreferences.boundaries[feederPreferences.boundaries.size-1]==1.0)
    }

    @Test
    fun assertSavedPreferences(){
        val current = Firebase.auth.currentUser
        Log.d("CURRENT USER", current.toString())
        assertNotNull(current)
        var flag = true
        var i = 0
        while(flag && i<feederPreferences.boundaries.size-1){
            flag = feederPreferences.boundaries[i]<=1.0
            i+=1
        }
        assert(flag && feederPreferences.boundaries[feederPreferences.boundaries.size-1]==1.0)
    }

}