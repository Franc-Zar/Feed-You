package com.example.app1.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.app1.R
import com.google.android.material.appbar.MaterialToolbar

class AccountActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)


        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val password_reset = findViewById<TextView>(R.id.password_reset)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        password_reset.setOnClickListener {

            switch_activity = Intent(this, PasswordChangeActivity::class.java)
            startActivity(switch_activity)

        }

    }
}