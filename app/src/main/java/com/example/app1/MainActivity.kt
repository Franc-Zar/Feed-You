package com.example.app1

import CustomAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app1.model.NewsData
import com.example.app1.settings.menu.MenuActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var switch_activity: Intent
    private var current_user = Firebase.auth.currentUser!!
    private lateinit var rv : RecyclerView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar, menu)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        val searchView: SearchView = menuItem?.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                newText?.let { filter(it) }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(current_user != Firebase.auth.currentUser!!){
            current_user = Firebase.auth.currentUser!!
        }

        if (!current_user.isAnonymous and !intent.hasExtra("INITIALIZED")) {
            //nome db: feed-you-ca52a-default-rtdb
            database = FirebaseDatabase.getInstance("https://feed-you-ca52a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users").child(current_user.uid) //.child(getString(R.string.firebase_users))

            val langPref = database.child("lang")
            val langLocalPref = getSharedPreferences(getString(R.string.lang), MODE_PRIVATE)

            langPref.get().addOnSuccessListener {
                val lang = it.value
                if (lang != null) {
                    with(langLocalPref.edit()) {
                        putString(getString(R.string.lang), lang.toString())
                        apply()
                    }
                }
            }

            val topicPref = database.child("topics")
            val topicLocalPref = getSharedPreferences(getString(R.string.prefTopics), MODE_PRIVATE)
            topicPref.get().addOnSuccessListener {
                val topic = it.value
                if (topic != null) {
                    with(topicLocalPref.edit()) {
                        putString(getString(R.string.prefTopics), topic.toString())
                        apply()
                    }
                }
            }

            topicPref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.value
                    if (value != null){
                        Log.d("DATA UPDATED", "Value is: $value")
                        database.child("topics").setValue(value)
                        with(topicLocalPref.edit()) {
                            putString(getString(R.string.prefTopics), value.toString())
                            apply()
                        }
                        val intent = Intent(baseContext, MainActivity::class.java).apply {
                            putExtra("INITIALIZED", true)
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("ERROR UPDATING DATA", "Failed to read value.", error.toException())
                }
            })
        }

        if(getSharedPreferences(getString(R.string.prefTopics),MODE_PRIVATE).all.isEmpty()){
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

    }

    override fun onStart() {
        super.onStart()
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        refreshLayout.setOnRefreshListener {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            myUpdateOperation()
            refreshLayout.isRefreshing = false
        }

        myUpdateOperation()
    }

    fun myUpdateOperation(){
        val pref =getSharedPreferences(getString(R.string.prefTopics),Context.MODE_PRIVATE)
        if(current_user != Firebase.auth.currentUser!!){
            current_user = Firebase.auth.currentUser!!
        }
        if (!current_user.isAnonymous and !intent.hasExtra("INITIALIZED")) {
            //nome db: feed-you-ca52a-default-rtdb
            database = FirebaseDatabase.getInstance("https://feed-you-ca52a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("users").child(current_user.uid) //.child(getString(R.string.firebase_users))

            val langPref = database.child("lang")
            val langLocalPref = getSharedPreferences(getString(R.string.lang), MODE_PRIVATE)

            langPref.get().addOnSuccessListener {
                val lang = it.value
                if (lang != null) {
                    with(langLocalPref.edit()) {
                        putString(getString(R.string.lang), lang.toString())
                        apply()
                    }
                }
            }

            val topicPref = database.child("topics")
            val topicLocalPref = getSharedPreferences(getString(R.string.prefTopics), MODE_PRIVATE)
            topicPref.get().addOnSuccessListener {
                val topic = it.value
                if (topic != null) {
                    with(topicLocalPref.edit()) {
                        putString(getString(R.string.prefTopics), topic.toString())
                        apply()
                    }
                }
            }

            topicPref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.value
                    if (value != null){
                        Log.d("DATA UPDATED", "Value is: $value")
                        database.child("topics").setValue(value)
                        with(topicLocalPref.edit()) {
                            putString(getString(R.string.prefTopics), value.toString())
                            apply()
                        }
                        val intent = Intent(baseContext, MainActivity::class.java).apply {
                            putExtra("INITIALIZED", true)
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("ERROR UPDATING DATA", "Failed to read value.", error.toException())
                }
            })
        }

        if(pref.all.isNotEmpty()){
            if (!pref.all.getValue(getString(R.string.prefTopics))?.equals("[]")!!) {
                val feeder = CustomFeeder(this)
                rv = findViewById<RecyclerView>(R.id.rv)
                rv.setHasFixedSize(true)
                //Non servirebbe, il linear layout è quello di default
                rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                try {
                    val link = getSharedPreferences(getString(R.string.feedLink), Context.MODE_PRIVATE).all
                    if(link.isEmpty()){
                        feeder.setFeed(rv, findViewById(R.id.loading_icon))
                    }else{
                        feeder.setFeedFromLink(link[getString(R.string.feedLink)].toString(), rv, findViewById<ProgressBar>(R.id.loading_icon))
                    }
                } catch (e: CancellationException) {
                    Log.d("Coroutine failure", e.stackTrace.toString())
                    val intent = Intent(this, ErrorActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun filter(text: String) {
        try {
            val news = (rv.adapter as CustomAdapter).mList
            // creating a new array list to filter our data.
            val filteredlist = mutableListOf<NewsData>()
            // running a for loop to compare elements.
            for (item in news) { // news array
                // checking if the entered string matched with any item of our recycler view.
                if (item.title.lowercase().contains(text.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
            }
            if (filteredlist.isEmpty()) {
                // if no item is added in filtered list we are
                // displaying a toast message as no data found.
                Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            } else {
                (rv.adapter as CustomAdapter).filterList(filteredlist)
            }
        }catch (e:Exception){
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
    }
}