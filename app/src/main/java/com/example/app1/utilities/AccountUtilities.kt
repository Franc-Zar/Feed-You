package com.example.app1.utilities

import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

/** Classe di funzionalità utili per la gestione degli account
 */
class AccountUtilities {

    companion object {

        /** la password deve avere almeno una cifra, almeno un carattere minuscolo, almeno un carattere maiuscolo, almeno un carattere speciale,
        almeno una lunghezza di 6 caratteri
        */
        private val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[{}@#$%^&+=*?'_ç£!<>])(?=\\S+$).{6,}$"
        private val pattern = Pattern.compile(passwordRegex)

        /** metodo di verifica del rispetto della password policy
         */
        fun String.isValidPassword(): Boolean {

            val matcher = pattern.matcher(this)
            return matcher.matches()

        }

        /** metodo di restituzione del nome associato all'account Twitter
         */
        fun getTwitterName(): String? {

            for(provider in Firebase.auth.currentUser!!.providerData)
                if(provider.providerId == TwitterAuthProvider.PROVIDER_ID)
                    return provider.displayName.toString()

            return null

        }

        /** metodo di verifica del collegamento di un dato tipo di account social all'account dell'applicazione
         */
        fun isSocialLinked(socialProviderID: String): Boolean {

            val userProviders = checkUserProviders()

            if(userProviders.contains(socialProviderID))
                return true

            return false

        }

        /** metodo di restituzione delle tipologie di social account associate all'account dell'appllicazione
         */
        private fun checkUserProviders(): ArrayList<String> {

            val userProviders = ArrayList<String>()

            for(provider in Firebase.auth.currentUser!!.providerData) {

                userProviders.add(provider.providerId)

            }

            return userProviders

        }
    }
}