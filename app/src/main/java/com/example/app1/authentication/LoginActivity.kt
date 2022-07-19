package com.example.app1.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.MainActivity
import com.example.app1.R
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var switch_activity: Intent
    private lateinit var email: EditText
    private lateinit var password: EditText

    public override fun onRestart() {
        super.onRestart()

        email.setText("")
        password.setText("")

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //solo per test purposessssss
        val currentUser = null


        if (currentUser != null) {

            switch_activity = Intent(this, MainActivity::class.java)
            startActivity(switch_activity)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        val sign_in = findViewById<Button>(R.id.sign_in)
        val sign_up = findViewById<TextView>(R.id.sign_up)
        val twitter_login = findViewById<Button>(R.id.twitter_login)
        val google_login = findViewById<Button>(R.id.google_login)
        val forgot_password = findViewById<TextView>(R.id.forgot_password)

        sign_up.setOnClickListener {

            //cambio activity --> signup
            switch_activity = Intent(this, SignUpActivity::class.java)
            startActivity(switch_activity)

        }

        twitter_login.setOnClickListener {

            switch_activity = Intent(this, TwitterActivity::class.java)
            startActivity(switch_activity)

        }

        google_login.setOnClickListener {

            switch_activity = Intent(this, GoogleActivity::class.java)
            startActivity(switch_activity)

        }

        forgot_password.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta --> fatto
            switch_activity = Intent(this, PasswordRecoverActivity::class.java)
            startActivity(switch_activity)

        }

        //login email + password
        sign_in.setOnClickListener {

            val email_chosen = email.text.toString().trim()
            val password_chosen = password.text.toString().trim()

            if (email_chosen != "" && password_chosen != "") {

                auth.signInWithEmailAndPassword(email_chosen, password_chosen)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser

                            Toast.makeText(
                                baseContext, "Login successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            switch_activity = Intent(this, MainActivity::class.java)
                            startActivity(switch_activity)

                        } else {
                            // If sign in fails, display a message to the user.
                            when (task.exception) {

                                is FirebaseAuthInvalidCredentialsException ->

                                    Toast.makeText(
                                        baseContext, "Authentication failed: invalid credential!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                else ->

                                    Toast.makeText(
                                        baseContext, "Something went wrong, please try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                            }
                        }
                    }

            } else {

                Toast.makeText(
                    baseContext, "email and/or password missing!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}