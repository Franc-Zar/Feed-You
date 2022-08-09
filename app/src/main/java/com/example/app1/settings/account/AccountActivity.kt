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
import com.example.app1.authentication.LoginActivity
import com.example.app1.authentication.TwitterActivity
import com.example.app1.utilities.AccountUtilities.Companion.getTwitterName
import com.example.app1.utilities.AccountUtilities.Companion.isSocialLinked
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AccountActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private lateinit var twitterConnect: Button
    private lateinit var googleConnect: Button
    private lateinit var accountEmail: TextView
    private lateinit var emailDecoration: TextView
    private lateinit var connectSocials: TextView
    private lateinit var passwordReset: TextView
    private lateinit var accountName: TextView
    private lateinit var nameDecoration: TextView

    private fun deleteAccount() {

        Firebase.auth.currentUser!!.delete()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Firebase.auth.signOut()

                    Toast.makeText(
                        baseContext, "Account deleted successfully. Bye!",
                        Toast.LENGTH_SHORT
                    ).show()

                    switch_activity = Intent(this, LoginActivity::class.java)
                    switch_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(switch_activity)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                } else {

                    when(task.exception) {

                        is FirebaseAuthInvalidUserException ->

                            Toast.makeText(
                                baseContext, "Something went wrong: Account could already be deleted.",
                                Toast.LENGTH_SHORT
                            ).show()

                        is FirebaseAuthRecentLoginRequiredException -> {

                            Firebase.auth.signOut()
                            finish()

                            Toast.makeText(
                                baseContext, "Authentication time expired: please sign in again.",
                                Toast.LENGTH_SHORT
                            ).show()

                            switch_activity = Intent(this, LoginActivity::class.java)
                            switch_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(switch_activity)

                        }
                    }
                }
            }
    }


    private fun createAlert(socialProviderID: String?, alertType: String) {

        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setView(layoutInflater.inflate(R.layout.alert_feed_you,null))

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "Cancel"
        ) {
                dialog, which -> dialog.dismiss()
        }

        when(alertType) {

            "social" -> {

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Unlink"
            ) {
                    dialog, which -> unlinkSocial(socialProviderID!!)
            }

            alertDialog.show()
            val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)

            if(socialProviderID == GoogleAuthProvider.PROVIDER_ID)
                alertMessage?.setText(R.string.googleAlert)
            else if (socialProviderID == TwitterAuthProvider.PROVIDER_ID)
                alertMessage?.setText(R.string.twitterAlert)

            }

            "delete" -> {

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Delete"
            ) {
                    dialog, which -> deleteAccount()
            }

            alertDialog.show()

            val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)
            alertMessage?.setText(R.string.deleteAlert)

            }
        }
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

            accountEmail.setText(Firebase.auth.currentUser!!.email)

            if(Firebase.auth.currentUser!!.displayName == null ||
                Firebase.auth.currentUser!!.displayName == "") {

                accountName.setVisibility(View.GONE)
                nameDecoration.setVisibility(View.GONE)

            } else
                accountName.setText(Firebase.auth.currentUser!!.displayName)

        } else if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID)) {

            accountName.setText(getTwitterName())

            connectSocials.setVisibility(View.GONE)
            googleConnect.setVisibility(View.GONE)
            twitterConnect.setVisibility(View.GONE)

            passwordReset.setVisibility(View.GONE)

            accountEmail.setVisibility(View.GONE)
            emailDecoration.setVisibility(View.GONE)

        } else if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

            accountEmail.setText(Firebase.auth.currentUser!!.email)
            accountName.setText(Firebase.auth.currentUser!!.displayName)

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

        val deleteAccount = findViewById<TextView>(R.id.delete_account)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
<<<<<<< HEAD
        passwordReset = findViewById(R.id.select)
=======
        passwordReset = findViewById(R.id.change_password)
>>>>>>> 5e13186e000cba08d17c5c45d48ebac32bc1cb2e
        accountEmail = findViewById(R.id.account_email)
        twitterConnect = findViewById(R.id.twitter_connect)
        googleConnect = findViewById(R.id.google_connect)
        emailDecoration = findViewById(R.id.email)
        connectSocials = findViewById(R.id.connect_socials)
        accountName = findViewById(R.id.account_name)
        nameDecoration = findViewById(R.id.name_decoration)

        setSupportActionBar(toolbar)
        setAccountLayout()

        deleteAccount.setOnClickListener {

            createAlert(null, "delete")

        }


        toolbar.setNavigationOnClickListener {

            finish()

        }

        passwordReset.setOnClickListener {

            switch_activity = Intent(this, PasswordChangeActivity::class.java)
            startActivity(switch_activity)

        }


        twitterConnect.setOnClickListener{

            if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID)) {

                createAlert(TwitterAuthProvider.PROVIDER_ID, "social")

            } else {

                switch_activity = Intent(this, TwitterActivity::class.java)
                switch_activity.putExtra("requestType", "linkAccount")
                startActivity(switch_activity)

            }
        }

        googleConnect.setOnClickListener {

            if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

                createAlert(GoogleAuthProvider.PROVIDER_ID, "social")

            } else {

                switch_activity = Intent(this, GoogleActivity::class.java)
                switch_activity.putExtra("requestType", "linkAccount")
                startActivity(switch_activity)

            }
        }
    }
}