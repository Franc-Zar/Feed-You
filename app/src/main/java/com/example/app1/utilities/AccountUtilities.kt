package com.example.app1.utilities

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class AccountUtilities {

    companion object {

        /** la password deve avere almeno una cifra, almeno un carattere minuscolo, almeno un carattere maiuscolo, almeno un carattere speciale,
        almeno una lunghezza di 6 caratteri
        */
        private val password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[{}@#$%^&+=*?'_ç£!<>])(?=\\S+$).{6,}$"
        private val pattern = Pattern.compile(password_regex)

        fun String.isValidPassword(): Boolean {

            val matcher = pattern.matcher(this)
            return matcher.matches()

        }

        fun getTwitterName(): String? {

            for(provider in Firebase.auth.currentUser!!.providerData)
                if(provider.providerId == TwitterAuthProvider.PROVIDER_ID)
                    return provider.displayName.toString()

            return null

        }


        fun isSocialLinked(socialProviderID: String): Boolean {

            val userProviders = checkUserProviders()

            if(userProviders.contains(socialProviderID))
                return true

            return false

        }

        private fun checkUserProviders(): ArrayList<String> {

            val userProviders = ArrayList<String>()

            for(provider in Firebase.auth.currentUser!!.providerData) {

                userProviders.add(provider.providerId)

            }

            return userProviders

        }
    }
}
