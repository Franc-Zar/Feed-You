package com.example.app1

import CustomAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatDelegate
=======
import android.widget.SearchView
>>>>>>> 9ce22cb1f9938d440f9b7e58d88dd9c590314bee
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app1.model.FeederPreferences
import com.example.app1.model.NewsData
import com.example.app1.model.User
import com.example.app1.settings.menu.MenuActivity
import com.example.app1.settings.menu.ThemePreferences
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var switch_activity: Intent
    private val current_user = Firebase.auth.currentUser!!
<<<<<<< HEAD
    private lateinit var themePreferences: ThemePreferences
=======
    private lateinit var rv : RecyclerView
>>>>>>> 9ce22cb1f9938d440f9b7e58d88dd9c590314bee

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

        if (!current_user.isAnonymous) {
            database = Firebase.database.reference
            val user = User(current_user.email, current_user.email)
            database.child(getString(R.string.firebase_users)).child(current_user.uid).setValue(user)

            val langPref = database.child(getString(R.string.firebase_users)).child(current_user.uid)
                .child("lang")
            val langLocalPref = getSharedPreferences(getString(R.string.lang), Context.MODE_PRIVATE)
            langPref.get().addOnSuccessListener {
                if (it.value != null) {
                    with(langLocalPref.edit()) {
                        putString(getString(R.string.lang), it.value.toString())
                        apply()
                    }
                }
            }

            val topicPref = database.child(getString(R.string.firebase_users))
                .child(current_user.uid)
                .child("topics")
            val topicLocalPref = getSharedPreferences(getString(R.string.prefTopics), Context.MODE_PRIVATE)

            topicPref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue()
                    if (value != null){
                        Log.d("DATA UPDATED", "Value is: $value")
                        with(topicLocalPref.edit()) {
                            putString(getString(R.string.prefTopics), value.toString())
                            apply()
                        }
                    }
                    /**if (value != null) {
                        val feederPreferences = FeederPreferences(context = baseContext)
                        //feederPreferences.setFavouriteTopics(value)
                    }**/
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
        if(pref.all.isNotEmpty()){
            if (!pref.all.getValue(getString(R.string.prefTopics))?.equals("[]")!!) {
                val feeder = CustomFeeder(this)
                rv = findViewById<RecyclerView>(R.id.rv)
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

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = mutableListOf<NewsData>()
        val news = (rv.adapter as CustomAdapter).mList
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
    }


    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
    }
}