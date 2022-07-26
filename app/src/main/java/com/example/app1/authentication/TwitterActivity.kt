package com.example.app1.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.MainActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class TwitterActivity : AppCompatActivity() {

    private val auth = Firebase.auth
    private lateinit var switch_activity: Intent
    private val provider = OAuthProvider.newBuilder(TwitterAuthProvider.PROVIDER_ID)
    private val pending_result: Task<AuthResult>? = auth.pendingAuthResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (auth.currentUser != null) {

            linkTwitterAccount()

        } else {

            if (pending_result != null) {

                pending_result_handle()

            } else {

                twitterSignIn()

            }

        }

    }


    private fun pending_result_handle() {

        // There's something already here! Finish the sign-in for your user.
        pending_result!!
            .addOnSuccessListener(
                OnSuccessListener<AuthResult?> {

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Login Successful.",
                        Toast.LENGTH_SHORT
                    ).show()

                    switch_activity = Intent(this, MainActivity::class.java)
                    startActivity(switch_activity)

                })
            .addOnFailureListener(
                OnFailureListener {
                    // Handle failure.
                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Something went wrong, please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                })
    }

    private fun linkTwitterAccount() {

        // The user is already signed-in.
        // The user is already signed-in.

        auth.currentUser!!
            .startActivityForLinkWithProvider( /* activity= */this, provider.build())
            .addOnSuccessListener {
                // Twitter credential is linked to the current user.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().
                // The OAuth secret can be retrieved by calling:
                // authResult.getCredential().getSecret().
                finish()
                overridePendingTransition(0, 0)

                Toast.makeText(
                    baseContext, "Twitter Account connected successfully!",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener {
                // Handle failure.
                finish()
                overridePendingTransition(0, 0)

                Toast.makeText(
                    baseContext, "Something went wrong, please try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


    }



    private fun twitterSignIn() {

        // There's no pending result so you need to start the sign-in flow.
        // See below.
        auth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener(
                OnSuccessListener<AuthResult?> {
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // authResult.getCredential().getAccessToken().
                    // The OAuth secret can be retrieved by calling:
                    // authResult.getCredential().getSecret().

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Login Successful.",
                        Toast.LENGTH_SHORT
                    ).show()

                    switch_activity = Intent(this, MainActivity::class.java)
                    startActivity(switch_activity)

                })

            .addOnFailureListener(
                OnFailureListener {
                    // Handle failure.

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Something went wrong, please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                })
    }
}
