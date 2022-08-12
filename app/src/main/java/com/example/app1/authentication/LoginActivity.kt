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

    private lateinit var switchActivity: Intent
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var themePreferences: ThemePreferences

    public override fun onRestart() {
        isSignedIn()
        email.setText("")
        password.setText("")
        super.onRestart()

    }

    public override fun onStart() {
        isSignedIn()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        themePreferences = ThemePreferences(applicationContext)
        themePreferences.setThemeSelected(themePreferences.getThemeSelected())

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

            switchActivity = Intent(this, AnonymousActivity::class.java)
            switchActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switchActivity)

        }

        /** Reindirizzamento a sign-up
         */
        signUp.setOnClickListener {

            switchActivity = Intent(this, SignUpActivity::class.java)
            switchActivity.putExtra("requestType","simpleSignUp")
            startActivity(switchActivity)

        }

        /** Reindirizzamento a sign-in tramite account Twitter
         */
        twitterSignIn.setOnClickListener {

            switchActivity = Intent(this, TwitterActivity::class.java)
            switchActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switchActivity.putExtra("requestType", "signIn")
            startActivity(switchActivity)

        }

        /** Reindirizzamento a sign-in tramite account Google
         */
        googleSignIn.setOnClickListener {

            switchActivity = Intent(this, GoogleActivity::class.java)
            switchActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switchActivity.putExtra("requestType", "signIn")
            startActivity(switchActivity)

        }

        /** Reindirizzamento a recupero password
         */
        forgotPassword.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta --> fatto
            switchActivity = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(switchActivity)

        }

        /** Reindirizzamento a sign-in email/password
         */
        simpleSignIn.setOnClickListener {

            val emailChosen = email.text.toString()
            val passwordChosen = password.text.toString()

            switchActivity = Intent(this, SimpleSignInActivity::class.java)
            switchActivity.putExtra("emailChosen", emailChosen)
            switchActivity.putExtra("passwordChosen", passwordChosen)
            switchActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switchActivity)

        }
    }

    /** metodo di verifica se l'utente è già loggato --> passaggio automatico a mainActivity
     */
    private fun isSignedIn() {

        if (Firebase.auth.currentUser != null) {

            switchActivity = Intent(this, MainActivity::class.java)
            switchActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            //overridePendingTransition(android.R.anim.anticipate_overshoot_interpolator, 0)
            startActivity(switchActivity)

        }
    }
}