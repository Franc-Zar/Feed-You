package com.example.app1.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app1.R
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Activity di recupero password mediante invio link di ripristino alla e-mail associata all'account
 */
class PasswordRecoveryActivity : AppCompatActivity() {

    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_password_recovery)

        val signIn = findViewById<TextView>(R.id.sign_in)
        val resetPassword = findViewById<Button>(R.id.reset_password)
        email = findViewById(R.id.email)

        resetPassword.setOnClickListener {

            recoverPassword()

        }

        signIn.setOnClickListener {

            super.finish()

        }

    }

    /** metodo di gestione eventi e invio link di ripristino password all'e-mail indicata
     */
    private fun recoverPassword() {

        val emailChosen = email.text.toString().trim()

        if(emailChosen != "") {

            Firebase.auth.sendPasswordResetEmail(emailChosen)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            baseContext, "Password reset sent: check your email",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()

                    } else {

                        when(task.exception) {

                            is FirebaseAuthEmailException ->

                                Toast.makeText(
                                    baseContext, "Password reset failed: email not sent, please try again",
                                    Toast.LENGTH_SHORT
                                ).show()

                            is FirebaseAuthInvalidUserException ->

                                Toast.makeText(
                                    baseContext, "Password reset failed: invalid email",
                                    Toast.LENGTH_SHORT
                                ).show()

                            is FirebaseAuthInvalidCredentialsException ->

                                Toast.makeText(
                                    baseContext, "Password reset failed: malformed email",
                                    Toast.LENGTH_SHORT
                                ).show()

                            else ->

                                Toast.makeText(
                                    baseContext, "Something went wrong, please try again",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }
                    }
                }

        } else {

            Toast.makeText(
                baseContext, "Email required",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

}