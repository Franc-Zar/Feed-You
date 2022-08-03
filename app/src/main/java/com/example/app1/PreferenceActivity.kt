package com.example.app1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val fragment1 = LangFragment()
        val fm: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(R.id.fragment, fragment1)
        transaction.commit()

        val button = findViewById<Button>(R.id.btn_block)
        button?.setOnClickListener {
            val f: Fragment =
                supportFragmentManager.findFragmentById(R.id.fragment) as Fragment
            if (f is LangFragment) {
                f.saveLang()
                val fragment2 = TopicsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment2).commit()
            }
            else if (f is TopicsFragment){
                f.saveTopics()
                val preferenceIntent = Intent(this, MainActivity::class.java)
                startActivity(preferenceIntent)
            }
        }
    }
}