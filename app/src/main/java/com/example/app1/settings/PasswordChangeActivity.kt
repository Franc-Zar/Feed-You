package com.example.app1.settings

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PasswordChangeActivity : AppCompatActivity() {

    private val current_user = Firebase.auth.currentUser
    private lateinit var new_password: EditText


    private fun checkCredential(): Boolean {

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider. --> valuto dopo se implementare il password reset per altri provider

        val credential = EmailAuthProvider.getCredential(current_user!!.email.toString(), new_password.text.toString())
        var success = false

        // Prompt the user to re-provide their sign-in credentials
        current_user.reauthenticate(credential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    success = true

                }
            }

        return success

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_password_change)
        val change_password = findViewById<Button>(R.id.change_password)
        val old_password = findViewById<EditText>(R.id.old_password)
        val confirm_password = findViewById<EditText>(R.id.confirm_password)
        new_password = findViewById(R.id.new_password)

        toolbar.setNavigationOnClickListener {

            finish()

        }

        change_password.setOnClickListener {

            val newPassword = new_password.text.toString()
            val confirmPassword = confirm_password.text.toString()

            if (checkCredential()) {

                if (newPassword != "" && confirmPassword != "") {

                    if (newPassword == confirmPassword) {


                        current_user!!.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    finish()


                                    Toast.makeText(
                                        baseContext, "Password changed successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    Toast.makeText(
                                        baseContext, "Something went wrong: please, try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                    } else {

                        Toast.makeText(
                            baseContext, "Password Reset failed: confirmation doesn't match",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            } else {

                Toast.makeText(
                    baseContext,"Password Reset failed: wrong password.",
                    Toast.LENGTH_SHORT
                    ).show()

            }
        }
    }
}