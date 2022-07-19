package com.example.app1.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordRecoverActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var email: EditText

    public override fun onRestart() {

        super.onRestart()
        email.setText("")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)

        val sign_in = findViewById<TextView>(R.id.sign_in)
        val reset_password = findViewById<Button>(R.id.reset_password)
        email = findViewById(R.id.email)

        reset_password.setOnClickListener{

            val email_chosen = email.text.toString().trim()

            if(email_chosen != "") {

                auth.sendPasswordResetEmail(email_chosen)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                baseContext, "Password reset sent: check your email",
                                Toast.LENGTH_SHORT
                            ).show()

                            super.finish()

                        } else {

                                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email_chosen).matches())

                                    when(task.exception) {

                                        is FirebaseAuthEmailException ->

                                            Toast.makeText(
                                                baseContext, "Password reset failed: email not sent, please try again.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        is FirebaseAuthInvalidUserException ->

                                            Toast.makeText(
                                                baseContext, "Password reset failed: invalid email.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        else ->

                                            Toast.makeText(
                                                baseContext, "Something went wrong, please try again.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                    }

                                else {

                                    Toast.makeText(
                                        baseContext, "Password reset failed: malformed email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }

            } else {

                Toast.makeText(
                    baseContext, "Email required.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


        sign_in.setOnClickListener {

            super.finish()

        }

    }
}