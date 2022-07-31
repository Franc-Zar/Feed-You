package com.example.app1.authentication

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.app1.MainActivity
import com.example.app1.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleActivity : AppCompatActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var switch_activity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUp()
        startAuthProcess()

    }

    private fun setUp() {

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()

    }

    private fun startAuthProcess() {

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->

                try {

                    val REQ_ONE_TAP = 2
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0
                    )

                } catch (e: IntentSender.SendIntentException) {

                    Log.e(TAG, "Couldn't start One Tap UI: " + e.localizedMessage)

                    finish()
                    overridePendingTransition(0, 0)

                }
            }
            .addOnFailureListener(this) { e -> // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(TAG, e.localizedMessage)

                finish()
                overridePendingTransition(0, 0)
            }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun linkAccountWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    finish()
                    overridePendingTransition(0, 0)

                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext, "Google Account linked successfully.",
                        Toast.LENGTH_SHORT
                    ).show()


                } else {

                    when (task.exception) {

                        is FirebaseAuthUserCollisionException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Provided Google Account is already associated.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        is FirebaseException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "User has already been linked to a Google Account.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        else -> {

                            // Handle failure.
                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Something went wrong, please try again.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
    }


    // [START auth_with_google]
    private fun signInWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    finish()
                    overridePendingTransition(0, 0)

                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext, "Login Successful.",
                        Toast.LENGTH_SHORT
                    ).show()

                    switch_activity = Intent(this, MainActivity::class.java)
                    startActivity(switch_activity)

                    } else {
                        // If sign in fails, display a message to the user.
                        finish()
                        overridePendingTransition(0, 0)

                        Toast.makeText(
                            baseContext, "Something went wrong, please try again.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }

        @Deprecated("Deprecated in Java")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            //resultCode == -1 corrisponde ad una interruzione della OneTapUI
            if(resultCode == -1) {

                if (requestCode == RC_SIGN_IN) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val requestType = intent.extras!!.getString("requestType").toString()

                        val account = task.getResult(ApiException::class.java)!!

                        if(requestType == "signIn")
                            signInWithGoogle(account.idToken!!)

                        else if (requestType == "linkAccount")
                            linkAccountWithGoogle(account.idToken!!)

                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately

                        Toast.makeText(
                            baseContext, "Something went wrong, please try again.",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                        overridePendingTransition(0, 0)

                    }
                }

            } else {

                finish()
                overridePendingTransition(0,0)

            }
        }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}
