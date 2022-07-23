package com.example.app1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var switch_activity: Intent

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar, menu)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
        //val user = User(name, email)
        //database.child("users").child(userId).setValue(user)    vale ricorsivamente sui parametri dell'oggetto, che sono a loro volta child
        //reset delle preferenze, finche non aggiungiamo il menu per modificarle
        /*
        val pref = getSharedPreferences(getString(R.string.lang),Context.MODE_PRIVATE)
        if (pref != null) {
        with(pref.edit()) {
        clear()
        apply()
        }
        }
        val spref = getSharedPreferences(getString(R.string.topics),Context.MODE_PRIVATE)
        if (spref != null) {
        with(spref.edit()) {
        clear()
        apply()
        }
        }

        val tpref = getSharedPreferences(getString(R.string.prefTopics),Context.MODE_PRIVATE)
        if (spref != null) {
        with(tpref.edit()) {
        clear()
        apply()
        }
        }
        */

        if(getSharedPreferences(getString(R.string.topics),MODE_PRIVATE).all.isEmpty() or getSharedPreferences(getString(R.string.prefTopics),MODE_PRIVATE).all.isEmpty()){
            val preferenceIntent = Intent(this, PreferenceActivity::class.java)
            startActivity(preferenceIntent)
        }
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            switch_activity = Intent(this, MenuActivity::class.java)
            startActivity(switch_activity)
        }
        /*
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.app_bar_search -> {
                    ;
                }
                else -> false
            }
        }
        */
    }

    override fun onStart() {
        super.onStart()
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

    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
    }
}