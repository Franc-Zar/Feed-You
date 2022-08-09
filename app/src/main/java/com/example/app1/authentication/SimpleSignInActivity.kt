package com.example.app1.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.MainActivity
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Activity di sign-in email/password
 */
class SimpleSignInActivity : AppCompatActivity() {

    private lateinit var switchActivity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simpleSignIn()

    }

    /** metodo di gestione dell'accesso email/password e di eventuali errori
     */
    private fun simpleSignIn() {

        val emailChosen = intent.extras!!.getString("emailChosen").toString()
        val passwordChosen = intent.extras!!.getString("passwordChosen").toString()

        if (emailChosen != "" && passwordChosen != "") {

            Firebase.auth.signInWithEmailAndPassword(emailChosen, passwordChosen)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        finish()

                        Toast.makeText(
                            baseContext, "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        switchActivity = Intent(this, MainActivity::class.java)
                        startActivity(switchActivity)

                    } else {

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
                                    baseContext, "Account temporarily disabled due to many failed login attempts: restore it by resetting your password or try again later",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            is FirebaseAuthInvalidUserException -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed: user with email: " + emailChosen + " doesn't exists",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            else -> {

                                finish()
                                overridePendingTransition(0, 0)

                                Toast.makeText(
                                    baseContext, "Something went wrong, please try again",
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