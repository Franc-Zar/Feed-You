package com.example.app1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var switch_activity: Intent
    private lateinit var provider: OAuthProvider.Builder

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
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

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

            val pending_result: Task<AuthResult>? = auth.pendingAuthResult
            provider = OAuthProvider.newBuilder("twitter.com");

            if (pending_result != null) {
                // There's something already here! Finish the sign-in for your user.
                pending_result
                    .addOnSuccessListener(
                        OnSuccessListener<AuthResult?> {

                            switch_activity = Intent(this, MainActivity::class.java)
                            startActivity(switch_activity)

                        })
                    .addOnFailureListener(
                        OnFailureListener {
                            // Handle failure.
                            Toast.makeText(
                                baseContext, "Something went wrong.",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
            } else {
                // There's no pending result so you need to start the sign-in flow.
                // See below.
                auth
                    .startActivityForSignInWithProvider( this, provider.build())
                    .addOnSuccessListener(
                        OnSuccessListener<AuthResult?> {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // authResult.getCredential().getSecret().
                            switch_activity = Intent(this, MainActivity::class.java)
                            startActivity(switch_activity)
                            Toast.makeText(
                                baseContext, "Login Successful.",
                                Toast.LENGTH_SHORT
                            ).show()

                        })
                    .addOnFailureListener(
                        OnFailureListener {
                            // Handle failure.
                            Toast.makeText(
                                baseContext, "Something went wrong.",
                                Toast.LENGTH_SHORT
                            ).show()
                        })


            }

        }

        google_login.setOnClickListener {

        }

        forgot_password.setOnClickListener {
            //devo creare la activity per rinviare la password all'email scelta --> fatto
            switch_activity = Intent(this, PasswordRecoverActivity::class.java)
            startActivity(switch_activity)

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
                        Toast.makeText(
                            baseContext, "Login successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        switch_activity = Intent(this, MainActivity::class.java)
                        startActivity(switch_activity)

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


