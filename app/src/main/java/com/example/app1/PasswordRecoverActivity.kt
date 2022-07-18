package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordRecoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)

        val sign_in = findViewById<TextView>(R.id.sign_in)
        val reset_password = findViewById<Button>(R.id.reset_password)
        val email = findViewById<EditText>(R.id.email)

        reset_password.setOnClickListener{

            val email_chosen = email.text.toString().trim()

            if(email_chosen != "") {

                Firebase.auth.sendPasswordResetEmail(email_chosen)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                baseContext, "Password reset sent: check your email",
                                Toast.LENGTH_SHORT
                            ).show()

                            super.finish()

                        } else {


                            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email_chosen).matches())

                                Toast.makeText(
                                    baseContext, "Something went wrong. Please, try Again.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            else {

                                Toast.makeText(
                                    baseContext, "Malformed email.",
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