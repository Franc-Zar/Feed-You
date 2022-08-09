package com.example.app1.settings.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.PreferenceActivity
import com.example.app1.R
import com.example.app1.authentication.LoginActivity
import com.example.app1.authentication.SignUpActivity
import com.example.app1.settings.account.AccountActivity
import com.example.app1.utilities.AccountUtilities.Companion.isSocialLinked
import com.example.app1.utilities.ThemePreferences
import com.example.app1.utilities.config.Companion.assistanceMail
import com.example.app1.utilities.config.Companion.domain
import com.example.app1.utilities.config.Companion.inviteLink
import com.example.app1.utilities.config.Companion.reportEmailBody
import com.example.app1.utilities.config.Companion.reportEmailSubject
import com.example.app1.utilities.linkUtilities.Companion.generateContentLink
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Activity del menu: il menu offre varie funzionalità di personalizzazione dell'esperienza utente
 */
class MenuActivity : AppCompatActivity() {

    private lateinit var switchActivity: Intent
    private lateinit var themePreferences: ThemePreferences

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_bar, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        themePreferences = ThemePreferences(this)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_account)
        val aboutUs = findViewById<TextView>(R.id.about_feed_you)
        val inviteFriends = findViewById<TextView>(R.id.connect_socials)
        val accountSettings = findViewById<TextView>(R.id.account_settings)
        val logout = findViewById<TextView>(R.id.logout)
        val reportProblem = findViewById<TextView>(R.id.report_problems)
        val themes = findViewById<TextView>(R.id.themes)

        val block = findViewById<TextView>(R.id.change_password)
        val pref = findViewById<TextView>(R.id.btn_pref)
        val singleFeed = findViewById<TextView>(R.id.btn_singleFeed)

        toolbar.setNavigationOnClickListener {
            finish()
        }



        block.setOnClickListener {
            val blockIntent = Intent(this, BlockActivity::class.java)
            startActivity(blockIntent)
        }

        pref.setOnClickListener {
            val prefIntent = Intent(this, PreferenceActivity::class.java)
            startActivity(prefIntent)
        }

        singleFeed.setOnClickListener {
            val singleFeedIntent = Intent(this, SingleFeedActivity::class.java)
            startActivity(singleFeedIntent)
        }

        reportProblem.setOnClickListener {

            reportProblem()

        }


        accountSettings.setOnClickListener {

            accountSettingsHandle()

        }

        aboutUs.setOnClickListener {

            switchActivity = Intent(this, AboutActivity::class.java)
            startActivity(switchActivity)

        }

        inviteFriends.setOnClickListener {

            onShareClicked(inviteLink, domain)

        }

        logout.setOnClickListener {

            createAlert("logout")

        }

        themes.setOnClickListener {

            createAlert("theme")

        }
    }

    /** metodo di creazione di interfaccia di condivisione link, dato url e domain
     */
    private fun onShareClicked(url: String, domain: String) {
        val link = generateContentLink(url, domain)

        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT, link.toString())

        startActivity(Intent.createChooser(share, "Share Link"))

    }

    /** metodo di creazione di alertDialog personalizzati
     */
    private fun createAlert(alertType: String) {

        when(alertType) {

            "logout" -> {

                val alertDialog = AlertDialog.Builder(this).create()

                alertDialog.setView(layoutInflater.inflate(R.layout.alert_feed_you,null))

                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL, "Cancel"
                ) {
                        dialog, which -> dialog.dismiss()
                }

                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "Logout"
                ) { dialog, which -> logout()
                }

                alertDialog.show()
                val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)

                alertMessage?.setText(R.string.logoutAlert)

            }

            "theme" -> {

                val themes = this.resources.getStringArray(R.array.app_themes)
                var themeSelected = themes[themePreferences.getThemeSelectedIndex()]

                AlertDialog.Builder(this, R.style.DialogTheme)
                    .setTitle("Change Theme")
                    .setIcon(R.drawable.logo)
                    .setSingleChoiceItems(themes, themePreferences.getThemeSelectedIndex()) { dialog, which ->

                        themeSelected = themes[which]

                    }.setNegativeButton("Cancel") { dialog, which ->

                        dialog.dismiss()

                    }
                    .setPositiveButton("Apply") { dialog, which ->

                        dialog.dismiss()
                        themePreferences.saveThemeSelected(themeSelected)
                        switchActivity = Intent(this, LoginActivity::class.java)
                        switchActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(switchActivity)
                        overridePendingTransition(android.R.anim.anticipate_interpolator, android.R.anim.fade_out)

                    }.show()

            }
        }
    }

    /** metodo di logout dell'utente dall'applicazione
     */
    private fun logout() {

        if(isSocialLinked(GoogleAuthProvider.PROVIDER_ID)) {

            val gso = GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            GoogleSignIn.getClient(this, gso).signOut()

        }

        Firebase.auth.signOut()

        switchActivity = Intent(this, LoginActivity::class.java)
        switchActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(switchActivity)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    /** metodo di creazione interfaccia per la segnalazione di problemi: l'utente viene reindirizzato ad un'applicazione esterna
     * di gestione e-mail, viene precompilata una e-mail da inviare al servizio di assistenza dell'applicazione, da personalizzare con
     * le informazioni relative allo specifico problema riscontrato
     */
    private fun reportProblem() {

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

    /** metodo di reindirizzamento alla sezione contenente informazioni relative all'account: in caso di utente anonimo, questi viene
     * reindirizzato alla activity di sign-up
     */
    private fun accountSettingsHandle() {

        if(isSocialLinked(TwitterAuthProvider.PROVIDER_ID) ||
            isSocialLinked(GoogleAuthProvider.PROVIDER_ID) ||
            isSocialLinked(EmailAuthProvider.PROVIDER_ID)
        ) {

            switchActivity = Intent(this, AccountActivity::class.java)
            startActivity(switchActivity)

        } else {

            Toast.makeText(
                baseContext, "You first need to create a Feed You Account.",
                Toast.LENGTH_SHORT
            ).show()

            switchActivity = Intent(this, SignUpActivity::class.java)
            switchActivity.putExtra("requestType", "createAccountRedirect")
            startActivity(switchActivity)

        }
    }
}