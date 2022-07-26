package com.example.app1.settings.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.settings.EmailAccountActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private var current_user = Firebase.auth.currentUser!!

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_bar, menu)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val aboutUs = findViewById<TextView>(R.id.about_feed_you)
        val inviteFriends = findViewById<TextView>(R.id.invite_friends)
        val account_settings = findViewById<TextView>(R.id.account_settings)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        account_settings.setOnClickListener {

            switch_activity = Intent(this, EmailAccountActivity::class.java)
            startActivity(switch_activity)

        }

        aboutUs.setOnClickListener {

            switch_activity = Intent(this, AboutActivity::class.java)
            startActivity(switch_activity)

        }

        inviteFriends.setOnClickListener {

        }
    }
}