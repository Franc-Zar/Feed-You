package com.example.app1.authentication

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.example.app1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var switch_activity: Intent

    private fun progress() {

        val progress_bar = findViewById<ProgressBar>(R.id.account_creation_progress)
        val animation = ObjectAnimator.ofInt(progress_bar, "progress", 0, 50)

        animation.setDuration(500)
        animation.setInterpolator(DecelerateInterpolator())
        animation.start()

    }

    public override fun onStart() {

        super.onStart()
        val currentUser = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progress()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val continue_sign_up = findViewById<Button>(R.id.sign_up)
        val sign_in = findViewById<TextView>(R.id.sign_in)
        val termini_condizioni = findViewById<CheckedTextView>(R.id.termini_condizioni)

        sign_in.setOnClickListener {

            super.finish()

        }

        termini_condizioni.setOnClickListener {

            termini_condizioni.toggle()

        }

        continue_sign_up.setOnClickListener {

            val email_chosen = email.text.toString().trim()
            val password_chosen = password.text.toString().trim()

            if (email_chosen != "" && password_chosen != "") {
                if (termini_condizioni.isChecked) {

                    auth.createUserWithEmailAndPassword(email_chosen, password_chosen)
                        .addOnCompleteListener(this) { task ->

                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user = auth.currentUser

                                switch_activity = Intent(this, LoginActivity::class.java)
                                startActivity(switch_activity)

                                Toast.makeText(
                                    baseContext, "User created successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                when(task.exception) {

                                    is FirebaseAuthUserCollisionException ->

                                        Toast.makeText(
                                            baseContext,
                                            "A user with email:" + email_chosen + " already exists!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    is FirebaseAuthWeakPasswordException ->

                                        Toast.makeText(
                                            baseContext,
                                            "Password should be at least 6 characters!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    else -> Toast.makeText(
                                        baseContext,
                                        "Something went wrong, please try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                    }

                } else {

                    Toast.makeText(
                        baseContext, "You must accept terms and policy!",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            } else {

                Toast.makeText(
                    baseContext, "Email and/or password missing!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}