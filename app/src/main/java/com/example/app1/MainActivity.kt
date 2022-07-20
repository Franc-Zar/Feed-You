package com.example.app1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CancellationException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //reset delle preferenze, finche non aggiungiamo il menu per modificarle
        /**
        val pref =getSharedPreferences(getString(R.string.lang),Context.MODE_PRIVATE)
        if (pref != null) {
                with(pref.edit()) {
                    clear()
                    apply()
            }
        }
        val spref =getSharedPreferences(getString(R.string.topics),Context.MODE_PRIVATE)
        if (spref != null) {
            with(spref.edit()) {
                clear()
                apply()
            }
        }
        val tpref =getSharedPreferences(getString(R.string.prefTopics),Context.MODE_PRIVATE)
        if (spref != null) {
            with(tpref.edit()) {
                clear()
                apply()
            }
        }
        **/

        if(getSharedPreferences(getString(R.string.topics),MODE_PRIVATE).all.isEmpty() or getSharedPreferences(getString(R.string.prefTopics),MODE_PRIVATE).all.isEmpty()){
            val preferenceIntent = Intent(this, PreferenceActivity::class.java)
            startActivity(preferenceIntent)
        }
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()

        val materialToolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refreshLayout.setOnRefreshListener {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            myUpdateOperation()
        }

        myUpdateOperation()

    }

    fun myUpdateOperation(){
        val pref =getSharedPreferences(getString(R.string.prefTopics),Context.MODE_PRIVATE)
        if(pref.all.isNotEmpty()){
            if (!pref.all.getValue(getString(R.string.prefTopics))?.equals("[]")!!) {
                val feeder = CustomFeeder(this)
                val rv = findViewById<RecyclerView>(R.id.rv)
                rv.setHasFixedSize(true)
                //Non servirebbe, il linear layout è quello di default
                rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                try {
                    //feeder.setFeedFromLink("https://www.ultimouomo.com/feed", rv, findViewById<ProgressBar>(R.id.loading_icon))
                    feeder.setFeed(rv, findViewById(R.id.loading_icon))
                } catch (e: CancellationException) {
                    Log.d("Coroutine failure", e.stackTrace.toString())
                    val intent = Intent(this, ErrorActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}