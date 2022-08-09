package com.example.app1.settings.account

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.example.app1.utilities.AccountUtilities.Companion.isValidPassword
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PasswordChangeActivity : AppCompatActivity() {

    private val current_user = Firebase.auth.currentUser!!

    private fun passwordReset(newPassword: String) {

        current_user.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    finish()

                    Toast.makeText(
                        baseContext, "Password changed successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        baseContext,
                        "Something went wrong: please, try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_password_change)
        val change_password = findViewById<Button>(R.id.btn_continue)
        val confirm_password = findViewById<EditText>(R.id.confirm_password)
        val new_password = findViewById<EditText>(R.id.new_password)
        val old_password = findViewById<EditText>(R.id.old_password)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        change_password.setOnClickListener {

            val newPassword = new_password.text.toString()
            val confirmPassword = confirm_password.text.toString()
            val oldPassword = old_password.text.toString()
            val currentEmail = current_user.email.toString()

            if (oldPassword != "") {

                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider. --> valuto dopo se implementare il password reset per altri provider
                val user_credential = EmailAuthProvider.getCredential(currentEmail, oldPassword)

                current_user.reauthenticate(user_credential)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            if (newPassword != "" && confirmPassword != "") {

                                if (newPassword == confirmPassword) {

                                    if (newPassword.isValidPassword()) {

                                        passwordReset(newPassword)

                                    } else {

                                        Toast.makeText(
                                            baseContext,
                                            "Password Reset failed: new password too weak.",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }

                                } else {

                                    Toast.makeText(
                                        baseContext,
                                        "Password Reset failed: confirmation doesn't match.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            } else {

                                Toast.makeText(
                                    baseContext,
                                    "Password Reset failed: new password and/or confirmation missing.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        } else {

                            Toast.makeText(
                                baseContext,
                                "Password Reset failed: wrong password.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

            } else {

                Toast.makeText(
                    baseContext,"Password Reset failed: old password required.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}
