package com.example.app1.settings.menu

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.google.android.material.appbar.MaterialToolbar


/** Activity riportante una breve descrizione dell'applicazione (about us)
 */
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_about_us)
        val aboutUs = findViewById<TextView>(R.id.decoration_text)
        aboutUs.movementMethod = ScrollingMovementMethod()

        toolbar.setNavigationOnClickListener {

            finish()

        }
    }
}