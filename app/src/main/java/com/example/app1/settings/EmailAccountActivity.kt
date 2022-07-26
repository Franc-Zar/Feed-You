package com.example.app1.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.authentication.LoginActivity
import com.example.app1.authentication.TwitterActivity
import com.example.app1.utilities.AccountUtilities.Companion.linkWithProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailAccountActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private val current_user = Firebase.auth.currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_account)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val password_reset = findViewById<TextView>(R.id.password_reset)
        val twitterConnect = findViewById<Button>(R.id.twitter_connect)
        val googleConnect = findViewById<Button>(R.id.google_connect)
        val logout = findViewById<TextView>(R.id.logout)
        val email = findViewById<TextView>(R.id.current_email)

        email.setText("Email: " + current_user.email)

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        password_reset.setOnClickListener {

            for(provider in current_user.providerData) {

                if(provider.providerId == EmailAuthProvider.PROVIDER_ID) {

                    switch_activity = Intent(this, PasswordChangeActivity::class.java)
                    startActivity(switch_activity)
                    break

                }
            }

            Toast.makeText(
                baseContext,
                "You first need to create a Feed You account!",
                Toast.LENGTH_SHORT
            ).show()

        }

        twitterConnect.setOnClickListener{

            switch_activity = Intent(this, TwitterActivity::class.java)
            startActivity(switch_activity)

        }

        googleConnect.setOnClickListener {

            linkWithProvider(GoogleAuthProvider.PROVIDER_ID)

        }

        logout.setOnClickListener {

            Firebase.auth.signOut()

            switch_activity = Intent(applicationContext, LoginActivity::class.java)
            switch_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(switch_activity)

        }

    }
}