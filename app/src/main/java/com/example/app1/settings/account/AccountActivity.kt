package com.example.app1.settings.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.authentication.GoogleActivity
import com.example.app1.authentication.TwitterActivity
import com.example.app1.settings.PasswordChangeActivity
import com.example.app1.utilities.AccountUtilities.Companion.getTwitterName
import com.example.app1.utilities.AccountUtilities.Companion.isSocialLinked
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AccountActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private val currentUser = Firebase.auth.currentUser!!
    private lateinit var twitterConnect: Button
    private lateinit var googleConnect: Button
    private lateinit var accountID: TextView
    private lateinit var accountIDdecoration: TextView
    private lateinit var connectSocials: TextView
    private lateinit var passwordReset: TextView

    private fun createSocialAlert(socialProviderID: String) {

        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setView(layoutInflater.inflate(R.layout.alert_social,null))

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "Cancel"
        ) {
                dialog, which -> dialog.dismiss()
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "Unlink"
        ) {
                dialog, which -> unlinkSocial(socialProviderID)
        }

        alertDialog.show()

        val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)

        if(socialProviderID == GoogleAuthProvider.PROVIDER_ID)
            alertMessage?.setText(R.string.googleAlert)

        else if (socialProviderID == TwitterAuthProvider.PROVIDER_ID)
            alertMessage?.setText(R.string.twitterAlert)

    }


    private fun socialLinksHandle() {

        if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID))
            twitterConnect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icons8_twitter, 0, R.drawable.ic_baseline_check_circle_24 , 0)
        else
            twitterConnect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icons8_twitter, 0, 0, 0)

        if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID))
            googleConnect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icons8_google, 0, R.drawable.ic_baseline_check_circle_24 , 0)
        else
            googleConnect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icons8_google, 0, 0, 0)

    }

    private fun unlinkSocial(socialProviderID: String) {

        var socialName = ""

        if(socialProviderID == GoogleAuthProvider.PROVIDER_ID)
            socialName = "Google"
        else if(socialProviderID == TwitterAuthProvider.PROVIDER_ID)
            socialName = "Twitter"

        Firebase.auth.currentUser!!.unlink(socialProviderID)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    socialLinksHandle()

                    Toast.makeText(
                        baseContext, socialName + " Account unlinked successfully.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        baseContext, "Something went wrong. Please, try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun setAccountLayout() {

        if(isSocialLinked(EmailAuthProvider.PROVIDER_ID)) {

            accountID.setText(currentUser.email)

        } else if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID)) {

            accountID.setText(getTwitterName())
            accountIDdecoration.setCompoundDrawablesWithIntrinsicBounds(R.drawable.twitter_classic, 0, 0, 0)
            accountIDdecoration.setText("Name")
            connectSocials.setVisibility(View.GONE)
            googleConnect.setVisibility(View.GONE)
            twitterConnect.setVisibility(View.GONE)
            passwordReset.setVisibility(View.GONE)


        } else if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

            accountID.setText(currentUser.email)
            accountIDdecoration.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_icons8_google, 0, 0, 0)
            connectSocials.setVisibility(View.GONE)
            googleConnect.setVisibility(View.GONE)
            twitterConnect.setVisibility(View.GONE)
            passwordReset.setVisibility(View.GONE)

        }
    }

    override fun onStart() {
        socialLinksHandle()
        super.onStart()

    }

    override fun onResume() {
        socialLinksHandle()
        super.onResume()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        passwordReset = findViewById(R.id.password_reset)
        accountID = findViewById(R.id.accountID)
        twitterConnect = findViewById(R.id.twitter_connect)
        googleConnect = findViewById(R.id.google_connect)
        accountIDdecoration = findViewById(R.id.accountID_decoration)
        connectSocials = findViewById(R.id.connect_socials)

        setSupportActionBar(toolbar)

        setAccountLayout()

        toolbar.setNavigationOnClickListener {

            finish()

        }

        passwordReset.setOnClickListener {

            switch_activity = Intent(this, PasswordChangeActivity::class.java)
            startActivity(switch_activity)

        }


        twitterConnect.setOnClickListener{

            if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID)) {

                createSocialAlert(TwitterAuthProvider.PROVIDER_ID)

            } else {

                switch_activity = Intent(this, TwitterActivity::class.java)
                switch_activity.putExtra("requestType", "linkAccount")
                startActivity(switch_activity)

            }
        }

        googleConnect.setOnClickListener {

            if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

                createSocialAlert(GoogleAuthProvider.PROVIDER_ID)

            } else {

                switch_activity = Intent(this, GoogleActivity::class.java)
                switch_activity.putExtra("requestType", "linkAccount")
                startActivity(switch_activity)

            }
        }
    }
}