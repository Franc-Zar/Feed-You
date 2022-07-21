package com.example.app1.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.app1.MainActivity
import com.google.firebase.auth.FirebaseAuth

class AnonymousActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var switch_activity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(baseContext,
                        "Login Successful.",
                        Toast.LENGTH_SHORT).show()


                    switch_activity = Intent(this, MainActivity::class.java)
                    startActivity(switch_activity)


                } else {
                    // If sign in fails, display a message to the user.
                        finish()
                        overridePendingTransition(0, 0)

                    Toast.makeText(baseContext, "Something went wrong, please try again.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}