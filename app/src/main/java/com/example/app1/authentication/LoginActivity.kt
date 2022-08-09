package com.example.app1.authentication

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.app1.MainActivity
import com.example.app1.R
import com.example.app1.utilities.ThemePreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Activity di presentazione e reindirizzamento delle funzionalità di sign-in all'utente
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var themePreferences: ThemePreferences

    public override fun onResume() {
        super.onResume()

        email.setText("")
        password.setText("")

    }

    public override fun onStart() {
        super.onStart()
        isSignedIn()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        themePreferences = ThemePreferences(applicationContext)
        themePreferences.setThemeSelected(themePreferences.getThemeSelected()!!)

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        val simpleSignIn = findViewById<Button>(R.id.sign_in)
        val signUp = findViewById<TextView>(R.id.sign_up)
        val twitterSignIn = findViewById<Button>(R.id.twitter_connect)
        val googleSignIn = findViewById<Button>(R.id.google_connect)
        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        val anonymousLogin = findViewById<AppCompatImageButton>(R.id.anonymous_login)

        /** Reindirizzamento a sign-in anonimo
         */
        anonymousLogin.setOnClickListener{

            switch_activity = Intent(this, AnonymousActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switch_activity)

        }

        /** Reindirizzamento a sign-up
         */
        signUp.setOnClickListener {

            switch_activity = Intent(this, SignUpActivity::class.java)
            switch_activity.putExtra("requestType","simpleSignIn")
            startActivity(switch_activity)

        }

        /** Reindirizzamento a sign-in tramite account Twitter
         */
        twitterSignIn.setOnClickListener {

            switch_activity = Intent(this, TwitterActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switch_activity.putExtra("requestType", "signIn")
            startActivity(switch_activity)

        }

        /** Reindirizzamento a sign-in tramite account Google
         */
        googleSignIn.setOnClickListener {

            switch_activity = Intent(this, GoogleActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switch_activity.putExtra("requestType", "signIn")
            startActivity(switch_activity)

        }

        /** Reindirizzamento a recupero password
         */
        forgotPassword.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta --> fatto
            switch_activity = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(switch_activity)

        }

        /** Reindirizzamento a sign-in email/password
         */
        simpleSignIn.setOnClickListener {

            val emailChosen = email.text.toString()
            val passwordChosen = password.text.toString()

            switch_activity = Intent(this, SimpleSignInActivity::class.java)
            switch_activity.putExtra("emailChosen", emailChosen)
            switch_activity.putExtra("passwordChosen", passwordChosen)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switch_activity)

        }
    }

    /** metodo di verifica se l'utente è già loggato --> passaggio automatico a mainActivity
     */
    private fun isSignedIn() {

        if (Firebase.auth.currentUser != null) {

            switch_activity = Intent(this, MainActivity::class.java)
            startActivity(switch_activity)
            overridePendingTransition(android.R.anim.anticipate_interpolator, android.R.anim.fade_out)

        }
    }
}