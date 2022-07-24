package com.example.app1.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.example.app1.R
import com.google.android.material.appbar.MaterialToolbar

class MenuActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_bar, menu)
        val menuItem = menu?.findItem(R.id.app_bar_search)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val account_settings = findViewById<TextView>(R.id.account_settings)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        account_settings.setOnClickListener {

            switch_activity = Intent(this, AccountActivity::class.java)
            startActivity(switch_activity)

        }
    }
}