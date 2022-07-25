package com.example.app1.utilities

import android.net.wifi.hotspot2.pps.Credential
import android.widget.Toast
import com.example.app1.settings.AccountActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthCredential
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class AccountUtilities {



    // la password deve avere almeno una cifra, almeno un carattere minuscolo, almeno un carattere maiuscolo, almeno un carattere speciale,
    // almeno una lunghezza di 6 caratteri

    companion object {

        private var currentUser = Firebase.auth.currentUser!!

        fun String.isValidPassword(): Boolean {

            val password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[{}@#$%^&+=*?'_ç£!<>])(?=\\S+$).{6,}$"
            val pattern = Pattern.compile(password_regex)
            val matcher = pattern.matcher(this)
            return matcher.matches()

        }

        fun linkWithProvider(provider: String) {

            lateinit var credential: AuthCredential

            when(provider) {

                "Google" -> {



                }

            }

            currentUser.linkWithCredential(credential)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        currentUser = task.result?.user!!

                    }
                }
        }
    }
}

