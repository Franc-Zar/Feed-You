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
import com.example.app1.settings.menu.ThemePreferences
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
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
        isloggedIn()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        themePreferences = ThemePreferences(applicationContext)
        themePreferences.setThemeSelected(themePreferences.getThemeSelected()!!)

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.themes)
        password = findViewById(R.id.password)

        val simple_sign_in = findViewById<Button>(R.id.sign_in)
        val sign_up = findViewById<TextView>(R.id.sign_up)
        val twitter_login = findViewById<Button>(R.id.twitter_connect)
        val google_login = findViewById<Button>(R.id.google_connect)
        val forgot_password = findViewById<TextView>(R.id.forgot_password)
        val anonymous_login = findViewById<AppCompatImageButton>(R.id.anonymous_login)


        anonymous_login.setOnClickListener{

            switch_activity = Intent(this, AnonymousActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switch_activity)

        }

        sign_up.setOnClickListener {

            //cambio activity --> signup
            switch_activity = Intent(this, SignUpActivity::class.java)
            switch_activity.putExtra("requestType","simpleSignIn")
            startActivity(switch_activity)

        }

        twitter_login.setOnClickListener {

            switch_activity = Intent(this, TwitterActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switch_activity.putExtra("requestType", "signIn")
            startActivity(switch_activity)

        }

        google_login.setOnClickListener {

            switch_activity = Intent(this, GoogleActivity::class.java)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            switch_activity.putExtra("requestType", "signIn")
            startActivity(switch_activity)

        }

        forgot_password.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta --> fatto
            switch_activity = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(switch_activity)

        }

        //login email + password
        simple_sign_in.setOnClickListener {

            val email_chosen = email.text.toString()
            val password_chosen = password.text.toString()

            switch_activity = Intent(this, SimpleSignInActivity::class.java)
            switch_activity.putExtra("email_chosen", email_chosen)
            switch_activity.putExtra("password_chosen", password_chosen)
            switch_activity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(switch_activity)

        }
    }

    private fun isloggedIn() {

        if (Firebase.auth.currentUser != null) {

            switch_activity = Intent(this, MainActivity::class.java)
            startActivity(switch_activity)

        }
    }
}