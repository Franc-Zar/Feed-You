package com.example.app1

import MainPage
import MenuPage
import NewsPage
import Page
import SingleFeedPage
import TopBarPage
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.content.ContextCompat.startActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun start(){
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("INITIALIZED", true)
        }
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(context,intent, null)
    }

    @Test
    fun readNews()  {
        Page.on<MainPage>()
            .tapOnNews()
            .on<NewsPage>()
            .verify()
    }

    @Test
    fun openSingleFeedMode(){
        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.btn_singleFeed)

    }

}