package com.example.app1.settings.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app1.R
import com.google.android.material.appbar.MaterialToolbar

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_about_us)

        toolbar.setNavigationOnClickListener {

            finish()

        }
    }
}