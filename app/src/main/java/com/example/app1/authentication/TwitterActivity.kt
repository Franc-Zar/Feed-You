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

/** Activity di sign-in/sign-up tramite account Twitter (il primo sign-in crea un account dell'applicazione associato alle
 * credenziali dell'account fornito)
 */
class TwitterActivity : AppCompatActivity() {

    private lateinit var switchActivity: Intent
    private val provider = OAuthProvider.newBuilder(TwitterAuthProvider.PROVIDER_ID)
    private val pendingResult: Task<AuthResult>? = Firebase.auth.pendingAuthResult
    private val currentUser = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestType = intent.extras!!.getString("requestType").toString()

        if (requestType == "linkAccount") {

            linkTwitterAccount()

        } else if(requestType == "signIn") {

            if (pendingResult != null) {

                pendingResultHandle()

            } else {

                twitterSignIn()

            }
        }
    }

    /** metodo di gestione di richieste di autenticazione "pendenti", da ultimare prima di avviarne di nuove
     */
    private fun pendingResultHandle() {

        // There's something already here! Finish the sign-in for your user.
        pendingResult!!
            .addOnSuccessListener(
                OnSuccessListener<AuthResult?> {

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    switchActivity = Intent(this, MainActivity::class.java)
                    startActivity(switchActivity)

                })
            .addOnFailureListener(
                OnFailureListener {
                    // Handle failure.
                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(
                        baseContext, "Something went wrong, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                })
    }

    /** metodo di collegamento account applicazione esistente con un account Twitter
     */
    private fun linkTwitterAccount() {

        currentUser!!
            .startActivityForLinkWithProvider( /* activity= */this, provider.build())
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
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

                } else {

                    when (task.exception) {

                        is FirebaseAuthWebException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Operation canceled by user.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        is FirebaseAuthUserCollisionException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Provided Twitter Account is already associated.",
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


    /** metodo di sign-in tramite account Twitter fornito
     */
    private fun twitterSignIn() {

        // There's no pending result so you need to start the sign-in flow.
        // See below.
        Firebase.auth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
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

                    switchActivity = Intent(this, MainActivity::class.java)
                    startActivity(switchActivity)

                } else {

                    when (task.exception) {

                        is FirebaseAuthWebException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Authentication failed: operation canceled by user.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        is FirebaseAuthException -> {

                            finish()
                            overridePendingTransition(0, 0)

                            Toast.makeText(
                                baseContext, "Authentication failed: permissions not granted by user.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else -> {

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
}
