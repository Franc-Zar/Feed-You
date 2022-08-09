package com.example.app1

import AboutPage
import BlockPage
import MainPage
import MenuPage
import NewsPage
import Page
import PrefPage
import SingleFeedPage
import TopBarPage
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject
import org.junit.After
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

    @After
    fun clear(){
        with(context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).edit()){
            clear()
            apply()
        }
        with(context.getSharedPreferences(context.getString(R.string.feedLink), Context.MODE_PRIVATE).edit()){
            clear()
            apply()
        }

    }

    @Test
    fun readNews()  {
        Page.on<MainPage>()
            .tapOnNews()
            .on<NewsPage>()
            .verify()
    }

    @Test
    fun blockFromNews(){
        val oldBlocked = context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).all
        Page.on<MainPage>()
            .tapOnNews()
            .on<NewsPage>()
            .tapOnBlock()
            .verify()
        val newBlocked = context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).all
        assert(newBlocked.size > oldBlocked.size)
    }

    @Test
    fun blockFromMenu(){
        val oldBlocked = context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).all
        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.btn_blocked)
            .on<BlockPage>()
            .tapOnFeed()
        val newBlocked = context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).all
        assert(newBlocked.size != oldBlocked.size)  //Non semplicemente minore, poiché se già bloccato con la stessa azione lo sblocco
    }

    @Test
    fun openSingleFeedMode(){
        val pref = context.getSharedPreferences(context.getString(R.string.feedLink), Context.MODE_PRIVATE)
        with(pref.edit()){
            clear()
            apply()
        }

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
        assert(pref.all[context.getString(R.string.feedLink)]?.equals(feedLink) ?: false)
    }

    @Test
    fun resetSingleFeed(){
        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.btn_singleFeed)
            .on<SingleFeedPage>()
            .tapOnReset()
        val pref = context.getSharedPreferences(context.getString(R.string.feedLink), Context.MODE_PRIVATE)
        assert(pref.all.isEmpty())
    }

    @Test
    fun verifyAboutUs(){
        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.about_feed_you)
            .on<AboutPage>()
            .verify()
    }

    @Test
    fun changePrefs(){
        Page.on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnOption(R.id.btn_pref)
            .on<PrefPage>()
            .tapOnContinue()
            .selectTopics()
            .tapOnContinue()

        val lang = context.getSharedPreferences(context.getString(R.string.lang), Context.MODE_PRIVATE).all
        assert(lang[context.getString(R.string.lang)]?.equals("it") ?: false )
        val topics = context.getSharedPreferences(context.getString(R.string.prefTopics), Context.MODE_PRIVATE).all
        assert(topics.isNotEmpty())
    }
}