package com.example.app1.settings.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.authentication.AnonymousActivity
import com.example.app1.authentication.LoginActivity
import com.example.app1.authentication.SignUpActivity
import com.example.app1.settings.account.AccountActivity
import com.example.app1.utilities.AccountUtilities.Companion.isSocialLinked
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_bar, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val aboutUs = findViewById<TextView>(R.id.about_feed_you)
        val inviteFriends = findViewById<TextView>(R.id.connect_socials)
        val accountSettings = findViewById<TextView>(R.id.account_settings)
        val logout = findViewById<TextView>(R.id.logout)


        toolbar.setNavigationOnClickListener {

            finish()

        }

        accountSettings.setOnClickListener {

            if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID) ||
                isSocialLinked(GoogleAuthProvider.PROVIDER_ID) ||
                    isSocialLinked(EmailAuthProvider.PROVIDER_ID)
            ) {

                switch_activity = Intent(this, AccountActivity::class.java)
                startActivity(switch_activity)

            } else {

                Toast.makeText(
                    baseContext, "You first need to create a Feed You Account.",
                    Toast.LENGTH_SHORT
                ).show()

                switch_activity = Intent(this, SignUpActivity::class.java)
                switch_activity.putExtra("requestType", "createAccountRedirect")
                startActivity(switch_activity)

            }
        }

        aboutUs.setOnClickListener {

            switch_activity = Intent(this, AboutActivity::class.java)
            startActivity(switch_activity)

        }

        inviteFriends.setOnClickListener {

        }


        logout.setOnClickListener {

            Firebase.auth.signOut()

            switch_activity = Intent(this, LoginActivity::class.java)
            switch_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(switch_activity)

        }
    }
}
