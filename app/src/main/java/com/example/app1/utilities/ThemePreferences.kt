package com.example.app1.utilities

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.app1.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** Classe di utilità relative alla configurazione del tema visivo dell'applicazione
 */
class ThemePreferences(var context: Context) {

    private val theme = context.getSharedPreferences(context.getString(R.string.theme), Context.MODE_PRIVATE)

    /** metodo di salvataggio del tema scelto tra quelli proposti
     */
     fun saveThemeSelected(themeSelected: String) {

        with(theme.edit()) {
            putString(context.getString(R.string.theme), themeSelected)
            apply()

        }
    }

    /** metodo di reperimento del tema selezionato
     */
    fun getThemeSelected(): String? {

        return theme.getString(context.getString(R.string.theme), "Follow System")

    }

    /** metodo di reperimento dell'indice del tema selezionato, nell'array dei temi
     */
    fun getThemeSelectedIndex(): Int {

        val themes = context.resources.getStringArray(R.array.app_themes)
        return themes.indexOf(getThemeSelected())

    }

    /** metodo di setting, entro l'applicazione, del tema selezionato
     */
    fun setThemeSelected(themeSelected: String) {

        if(Firebase.auth.currentUser != null) {

            when (themeSelected) {

                "FeedYou-Light" -> {

                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                    saveThemeSelected(themeSelected)

                }

                "FeedYou-Dark" -> {

                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                saveThemeSelected(themeSelected)

                }

                "Follow System" -> {

                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    )
                    saveThemeSelected(themeSelected)
                }
            }

        } else {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )

            saveThemeSelected("Follow System")


        }

    }
}

