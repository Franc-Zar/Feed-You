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

    private lateinit var switchActivity: Intent
    private lateinit var progressBar: ProgressBar
    private lateinit var termsPolicy: CheckedTextView
    private lateinit var email: EditText
    private lateinit var password: EditText


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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_sign_up)

        progressBar = findViewById(R.id.sign_up_progress)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        val signUp = findViewById<Button>(R.id.sign_up)
        val signIn = findViewById<TextView>(R.id.sign_in)
        termsPolicy = findViewById(R.id.terms_policy)


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

            when (requestType) {

                "createAccountRedirect" -> {

                    finish()
                    switchActivity = Intent(this, LoginActivity::class.java)
                    startActivity(switchActivity)

                }

                "simpleSignUp" ->
                    finish()

            }
        }


        termsPolicy.setOnClickListener {

            termsPolicy.toggle()

            if (termsPolicy.isChecked)
                accountCreationProgress(20)
            else
                accountCreationProgress(-20)

        }


        signUp.setOnClickListener {

            signUpProcess()

        }
    }

    private fun signUpProcess() {

        val emailChosen = email.text.toString()
        val passwordChosen = password.text.toString()

        if (emailChosen != "" && passwordChosen != "") {

            if (termsPolicy.isChecked) {

                if (passwordChosen.isValidPassword()) {

                    val requestType = intent.extras!!.getString("requestType").toString()

                    when(requestType) {

                        "createAccountRedirect" ->
                            createAccountForAnonymous(emailChosen, passwordChosen)


                        "simpleSignUp" ->
                            simpleCreateAccount(emailChosen, passwordChosen)

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

    private fun createAccountForAnonymous(email: String, password: String) {

        val credential = EmailAuthProvider.getCredential(email, password)

        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    finish()

                    Toast.makeText(
                        baseContext, "Account created or linked successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                    switchActivity = Intent(this, AccountActivity::class.java)
                    startActivity(switchActivity)

                } else {

                    finish()

                    Toast.makeText(
                        baseContext, "Account created or linked successfully, but redirect failed",
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

                    Firebase.auth.signOut()

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
                                "Password should contain at least: 6 characters, 1 uppercase letter, 1 special character, 1 number!",
                                Toast.LENGTH_SHORT
                            ).show()

                        is FirebaseAuthInvalidCredentialsException ->

                            Toast.makeText(
                                baseContext,
                                "Malformed email",
                                Toast.LENGTH_SHORT
                            ).show()

                        else -> Toast.makeText(
                            baseContext,
                            "Something went wrong, please try again",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
    }
}