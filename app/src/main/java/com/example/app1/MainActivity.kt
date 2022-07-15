package com.example.app1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CancellationException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!getPreferences(Context.MODE_PRIVATE).all.isEmpty()){
            val preferenceIntent = Intent(this, PreferenceActivity::class.java)
            startActivity(preferenceIntent)
        }
        setContentView(R.layout.activity_main)

        val feeder = CustomFeeder(this)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.setHasFixedSize(true)
        //Non servirebbe, il linear layout è quello di default
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        try{
            feeder.setNews("https://www.ultimouomo.com/feed", rv, findViewById<ProgressBar>(R.id.loading_icon))
        }catch (e: CancellationException){
            Log.d("Coroutine failure", e.stackTrace.toString())
            val intent = Intent(this, ErrorActivity::class.java)
            startActivity(intent)
        }

        val materialToolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        materialToolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.favorite -> {
                    Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                    true
                }else -> false
            }
        }
    }

}