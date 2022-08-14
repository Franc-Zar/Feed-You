package com.example.app1

import BlockPage
import MainPage
import MenuPage
import NewsPage
import com.example.app1.utilities.Page
import SingleFeedPage
import TopBarPage
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.content.ContextCompat.startActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

@RunWith(AndroidJUnit4::class)
class NavigationTests {
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun start(){
        Firebase.auth.signInWithEmailAndPassword("test@test.com", "Test1#")
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("INITIALIZED", true)
        }
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(context,intent, null)
    }

    @After
    fun finish() {
        Firebase.auth.currentUser!!.delete()

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
            .tapOnOption(R.id.btn_continue)
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
}