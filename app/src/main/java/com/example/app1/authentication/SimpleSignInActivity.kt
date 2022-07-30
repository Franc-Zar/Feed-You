package com.example.app1.authentication

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.MainActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SimpleSignInActivity : AppCompatActivity() {

    private val auth = Firebase.auth
    private lateinit var switch_activity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email_chosen = intent.extras!!.getString("email_chosen").toString()
        val password_chosen = intent.extras!!.getString("password_chosen").toString()

        if (email_chosen != "" && password_chosen != "") {

            auth.signInWithEmailAndPassword(email_chosen, password_chosen)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        finish()

                        Toast.makeText(
                            baseContext, "Login successful.",
                            Toast.LENGTH_SHORT
                        ).show()

                        switch_activity = Intent(this, MainActivity::class.java)
                        startActivity(switch_activity)

                    } else {

                        // If sign in fails, display a message to the user.
                        when (task.exception) {

                            is FirebaseAuthInvalidCredentialsException -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext, "Authentication failed: invalid credential!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            is FirebaseTooManyRequestsException -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext, "Account temporarily disabled due to many failed login attempts: restore it by resetting your password or try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            is FirebaseAuthInvalidUserException -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed: user with email: " + email_chosen + " doesn't exists.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            else -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext, "Something went wrong, please try again.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }

                }

            } else {

                finish()
                overridePendingTransition(0, 0)

                Toast.makeText(
                    baseContext, "Email and/or password missing!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}