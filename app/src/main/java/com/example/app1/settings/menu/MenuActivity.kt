package com.example.app1.settings.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.authentication.LoginActivity
import com.example.app1.authentication.SignUpActivity
import com.example.app1.settings.account.AccountActivity
import com.example.app1.utilities.AccountUtilities.Companion.isSocialLinked
import com.example.app1.utilities.linkUtilities.Companion.generateContentLink
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.app1.utilities.config.Companion.assistanceMail
import com.example.app1.utilities.config.Companion.domain
import com.example.app1.utilities.config.Companion.inviteLink
import com.example.app1.utilities.config.Companion.reportEmailBody
import com.example.app1.utilities.config.Companion.reportEmailSubject
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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
        val reportProblem = findViewById<TextView>(R.id.report_problems)
        val themes = findViewById<Spinner>(R.id.spinner_themes)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        themes.setOnItemClickListener { adapterView, view, i, l ->

            if(themes.selectedItem.toString() == "FeedYou-Light")
                setTheme(android.R.style.Theme)

        }

        reportProblem.setOnClickListener {

            val reportBugIntent = Intent(Intent.ACTION_SEND)
            reportBugIntent.type = "message/rfc822"
            reportBugIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(assistanceMail))
            reportBugIntent.putExtra(Intent.EXTRA_SUBJECT, reportEmailSubject)
            reportBugIntent.putExtra(Intent.EXTRA_TEXT, reportEmailBody)
            reportBugIntent.putExtra(Intent.EXTRA_TITLE, "Choose an e-mail application")

            try {

                startActivity(Intent.createChooser(reportBugIntent, "Send mail..."))

            } catch (ex: ActivityNotFoundException) {

                Toast.makeText(
                    this,
                    "Operation failed: there are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
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

            onShareClicked(inviteLink, domain)

        }

        logout.setOnClickListener {

            logoutAlert()

        }
    }


    private fun onShareClicked(url: String, domain: String) {
        val link = generateContentLink(url, domain)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link.toString())

        startActivity(Intent.createChooser(intent, "Share Link"))

    }

    private fun logoutAlert() {

        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setView(layoutInflater.inflate(R.layout.alert_feed_you,null))

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "Cancel"
        ) {
                dialog, which -> dialog.dismiss()
        }

        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "Logout"
        ) {
                dialog, which -> logout()
        }

        alertDialog.show()
        val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)

        alertMessage?.setText(R.string.logoutAlert)

    }

    private fun logout() {

        if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

            val gso = GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            GoogleSignIn.getClient(this, gso).signOut()

        }

        Firebase.auth.signOut()

        switch_activity = Intent(this, LoginActivity::class.java)
        switch_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(switch_activity)

    }
}