package com.example.app1.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app1.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Activity di gestione del processo di sign-in anonimo
 */
class AnonymousActivity : AppCompatActivity() {

    private lateinit var switchActivity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anonymousSignIn()

    }

    /** metodo di sign-in anonimo, integrato con la gestione dell'esito positivo o negativo;
     * il sign-in anonimo è gestito dal modulo Authentication di Firebase creando un account corrispondente,
     * successivamente convertibile, se richiesto, in un account e-mail/password
     */
    private fun anonymousSignIn() {

        Firebase.auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(baseContext,
                        "Login Successful",
                        Toast.LENGTH_SHORT).show()

                    switchActivity = Intent(this, MainActivity::class.java)
                    startActivity(switchActivity)

                } else {

                    finish()
                    overridePendingTransition(0, 0)

                    Toast.makeText(baseContext, "Something went wrong, please try again",
                        Toast.LENGTH_SHORT).show()
                }


            }
        }
    }