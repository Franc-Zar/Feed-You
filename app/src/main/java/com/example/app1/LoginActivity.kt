package com.example.app1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //passa direttamente alla schermata principale
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val sign_in = findViewById<Button>(R.id.sign_in)
        val sign_up = findViewById<TextView>(R.id.sign_up)
        val twitter_login = findViewById<Button>(R.id.twitter_login)
        val google_login = findViewById<Button>(R.id.google_login)
        val forgot_password = findViewById<TextView>(R.id.forgot_password)






        sign_up.setOnClickListener {

            //cambio activity --> signup
            val go_sign_up = Intent(this, SignUpActivity::class.java)
            startActivity(go_sign_up)

        }



        twitter_login.setOnClickListener {

        }

        google_login.setOnClickListener {

        }


        forgot_password.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta
        }


        //login
        sign_in.setOnClickListener {

            if (email.text.toString().trim() != "" && password.text.toString().trim() != "") {

                auth.signInWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
                ).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        // sostituire toast con passaggio alla schermata principale
                        Toast.makeText(
                            baseContext, "OK",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
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


