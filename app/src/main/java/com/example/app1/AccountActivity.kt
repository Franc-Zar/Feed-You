package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)


        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        toolbar.setNavigationOnClickListener {

            finish()

        }

    }
}