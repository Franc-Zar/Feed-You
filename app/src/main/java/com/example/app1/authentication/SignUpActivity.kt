package com.example.app1.authentication

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.example.app1.R
import com.example.app1.settings.account.AccountActivity
import com.example.app1.settings.menu.MenuActivity
import com.example.app1.utilities.AccountUtilities.Companion.isValidPassword
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent

    private fun progress() {

        val progress_bar = findViewById<ProgressBar>(R.id.password_reset_progress)
        val animation = ObjectAnimator.ofInt(progress_bar, "progress", 0, 50)

        animation.setDuration(500)
        animation.setInterpolator(DecelerateInterpolator())
        animation.start()

    }

    public override fun onStart() {

        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sign_up)

        progress()

        val email = findViewById<EditText>(R.id.accountID_decoration)
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

                    if(password_chosen.isValidPassword()) {

                        val requestType = intent.extras!!.getString("requestType").toString()
                        val credential = EmailAuthProvider.getCredential(email_chosen, password_chosen)

                        if(requestType == "createAccountRedirect") {

                            Firebase.auth.currentUser!!.linkWithCredential(credential)
                                .addOnCompleteListener(this) { task ->

                                    if (task.isSuccessful) {

                                        finish()

                                        Toast.makeText(
                                            baseContext, "Account created successfully!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        switch_activity = Intent(this, AccountActivity::class.java)
                                        startActivity(switch_activity)

                                    } else {

                                        finish()

                                        Toast.makeText(
                                            baseContext, "Account created successfully, but redirect failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }

                        } else {

                                Firebase.auth.createUserWithEmailAndPassword(email_chosen, password_chosen)
                                .addOnCompleteListener(this) { task ->

                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information

                                        finish()

                                        Toast.makeText(
                                            baseContext, "Account created successfully!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    } else {

                                        when (task.exception) {

                                            is FirebaseAuthUserCollisionException ->

                                                Toast.makeText(
                                                    baseContext,
                                                    "A user with email: " + email_chosen + " already exists!",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            is FirebaseAuthWeakPasswordException ->

                                                Toast.makeText(
                                                    baseContext,
                                                    "Password should be at least 6 characters!",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            is FirebaseAuthInvalidCredentialsException ->

                                                Toast.makeText(
                                                    baseContext,
                                                    "Malformed email.",
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
                        }

                    } else {

                        Toast.makeText(
                            baseContext, "Password too weak!",
                            Toast.LENGTH_SHORT
                        ).show()
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