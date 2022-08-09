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
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

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
        val lang = context.getSharedPreferences(
            context.getString(R.string.lang),
            Context.MODE_PRIVATE).all[context.getString(R.string.lang)]
        val inputStream: InputStream = context.assets.open("feeds/$lang.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        val content = String(buffer)
        val json = JSONObject(content)
        val feedLink =json.getJSONArray("0").getString(0)

        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.btn_singleFeed)
            .on<SingleFeedPage>()
            .tapOnFeed()
            .back()
            .on<MenuPage>()
            .back()
            .on<MainPage>()
            .verify()

        assert(context.getSharedPreferences(context.getString(R.string.feedLink), Context.MODE_PRIVATE).all.isNotEmpty())
    }

}