package com.example.app1.utilities

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.app1.R

class ThemePreferences(var context: Context) {

    private val theme = context.getSharedPreferences(context.getString(R.string.theme), Context.MODE_PRIVATE)

     fun saveThemeSelected(themeSelected: String) {

        with(theme.edit()) {
            putString(context.getString(R.string.theme), themeSelected)
            apply()

        }
    }

    fun getThemeSelected(): String? {

        return theme.getString(context.getString(R.string.theme), "Follow System")

    }

    fun getThemeSelectedIndex(): Int {

        val themes = context.resources.getStringArray(R.array.app_themes)
        return themes.indexOf(getThemeSelected())

    }

    fun setThemeSelected(themeSelected: String) {

        when(themeSelected) {

            "FeedYou-Light" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)

            "FeedYou-Dark" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)

            "Follow System" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        }

        saveThemeSelected(themeSelected)

    }

}

