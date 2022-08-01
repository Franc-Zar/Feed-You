package com.example.app1.authentication

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View.OnFocusChangeListener
import android.view.Window
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.settings.account.AccountActivity
import com.example.app1.utilities.AccountUtilities.Companion.isValidPassword
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    private lateinit var switch_activity: Intent
    private lateinit var progressBar: ProgressBar


    private fun accountCreationProgress(increase: Int) {

        val animation = ObjectAnimator.ofInt(
                progressBar,
                "progress",
                progressBar.progress + increase
            )

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

        progressBar = findViewById(R.id.password_reset_progress)

        val email = findViewById<EditText>(R.id.email_decoration)
        val password = findViewById<EditText>(R.id.password)

        val signUp = findViewById<Button>(R.id.sign_up)
        val signIn = findViewById<TextView>(R.id.sign_in)
        val terminiCondizioni = findViewById<CheckedTextView>(R.id.termini_condizioni)


        email.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {
                // code to execute when EditText loses focus
                if (email.text.isEmpty())
                    accountCreationProgress(-40)
                else
                    accountCreationProgress(40)

                }
            })

        password.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {
                // code to execute when EditText loses focus
                if (password.text.isEmpty())
                    accountCreationProgress(-40)
                else
                    accountCreationProgress(40)

                }
            })


            signIn.setOnClickListener {

                val requestType = intent.extras!!.getString("requestType").toString()

                when(requestType) {

                    "createAccountRedirect" -> {

                        finish()
                        switch_activity = Intent(this, LoginActivity::class.java)
                        startActivity(switch_activity)

                    }

                    "simpleSignIn" ->
                        finish()

                }
            }


            terminiCondizioni.setOnClickListener {

                terminiCondizioni.toggle()

                if(terminiCondizioni.isChecked)
                    accountCreationProgress(20)
                else
                    accountCreationProgress(-20)

            }


            signUp.setOnClickListener {

                val email_chosen = email.text.toString()
                val password_chosen = password.text.toString()

                if (email_chosen != "" && password_chosen != "") {

                    if (terminiCondizioni.isChecked) {

                        if (password_chosen.isValidPassword()) {

                            val requestType = intent.extras!!.getString("requestType").toString()

                            when(requestType) {

                                "createAccountRedirect" ->
                                    createAccountForAnonymous(email_chosen, password_chosen)


                                "simpleSignIn" ->
                                    simpleCreateAccount(email_chosen, password_chosen)

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

    private fun createAccountForAnonymous(email: String, password: String) {

        val credential = EmailAuthProvider.getCredential(email, password)

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
    }

    private fun simpleCreateAccount(email: String, password: String) {

        Firebase.auth.createUserWithEmailAndPassword(email, password)
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
                                "A user with email: " + email + " already exists!",
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
}