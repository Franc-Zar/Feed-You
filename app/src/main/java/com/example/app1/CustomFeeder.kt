package com.example.app1

import CustomAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.model.FeederPreferences
import com.example.app1.model.NewsData
import kotlinx.coroutines.*
import org.json.JSONObject
import org.jsoup.Jsoup
import tw.ktrssreader.Reader
import tw.ktrssreader.kotlin.model.channel.RssStandardChannelData
import java.io.IOException
import java.io.InputStream
import java.net.URL


class CustomFeeder(var context: Context){
    private val preferences = FeederPreferences(context)

    private fun getLinksByTopic(): List<List<String>> {
        var content = ""
        try {
            val lang = context.getSharedPreferences(context.getString(R.string.lang), Context.MODE_PRIVATE).all[context.getString(R.string.lang)]
            val inputStream: InputStream = context.assets.open("feeds/$lang.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            content = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val links = mutableListOf<List<String>>()
        if (content != ""){
            val json = JSONObject(content)
            for(i in 0 until context.resources.getStringArray(R.array.topics_it).size) {
                for (k in json.keys()){
                    if (k.toInt() == i){
                        val topicLinks = json.getJSONArray(k)
                        val catLinks = mutableListOf<String>()
                        for (j in 0 until topicLinks.length()) {
                            catLinks.add(topicLinks.getString(j))
                        }
                        links.add(catLinks)
                        break
                    }
                }
                if (links.size<=i){
                    links.add(emptyList())
                }
            }
        }
        return links
    }

    fun setFeedFromLink(link: String, rv: RecyclerView, load: ProgressBar){
        val news = ArrayList<NewsData>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Reader.coRead<RssStandardChannelData>(link)
            val icon  = getIcon(result.items!![0].link.toString())
            var category = -1
            val links = getLinksByTopic()
            var inTopic = false
            for (i in 0 until links.size){
                for (feed in links[i]){
                    if (link==feed){
                        inTopic = true
                        break
                    }
                }
                if(inTopic){
                    category=i
                    break
                }
            }

            result.items!!.forEach {
                news.add(NewsData(it.title!!, it.description ?: "...", it.link!!, category,
                    it.guid, icon, link))
            }
            withContext(Dispatchers.Main){
                val adapter = CustomAdapter(news, context)
                rv.adapter = adapter
                load.visibility= View.INVISIBLE
            }
        }
    }

    fun setFeed(rv: RecyclerView, load: ProgressBar) {
        val news = mutableListOf<MutableList<NewsData>>()
        val links = getLinksByTopic()
        val blockedLinks = context.getSharedPreferences(context.getString(R.string.blocked), Context.MODE_PRIVATE).all

        CoroutineScope(Dispatchers.IO).launch {
            for (i in links.indices){
                news.add(mutableListOf())
                for (j in 0 until links[i].size){
                    var blocked = false
                    for(link in blockedLinks){
                        if(link.value == links[i][j])
                        blocked = true
                    }
                    if (blocked){
                        break
                    }
                    try {
                        val result = Reader.coRead<RssStandardChannelData>(links[i][j])
                        val icon = getIcon(result.items!![0].link.toString())
                        result.items!!.forEach {
                            news[i].add(NewsData(it.title!!,it.description ?: "...",
                                        it.link!!, i, it.guid, icon, links[i][j]))}
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
                news[i].shuffle()
            }

            val list = shuffler(news)
            withContext(Dispatchers.Main) {
                val adapter = CustomAdapter(list, context)
                rv.adapter = adapter
                load.visibility = View.INVISIBLE
            }
        }

    }

    fun shuffler(news: List<MutableList<NewsData>>): List<NewsData> {
        val list = mutableListOf<NewsData>()
        for (i in 0 until 40){
            val topic = preferences.getRandTopic()
            if (news[topic].isNotEmpty()){
                list.add(news[topic].removeFirst())
            }
        }
        return list
    }

    fun getIcon(link: String): Bitmap? {
        var icon : org.jsoup.nodes.Element? = null
        var image : Bitmap? = null
        try {
            val doc = URL(link).readText()

            icon = try {
                Jsoup.parse(doc).head().select("link[href~=.*\\.(ico|png)]").first()
            } catch (e: Exception) {
                try {
                    Jsoup.parse(doc).head().select("meta[itemprop=image]").first()
                } catch (e: Exception) {
                    null
                }
            }
            var favicon = icon?.attributes()?.get("href")
            if (favicon?.startsWith('/') == true) {
                favicon = favicon.substringAfter('/')
                if (favicon.startsWith('/')) {
                    favicon = favicon.substringAfter('/')
                }
                if (!favicon.startsWith("www")) {
                    val baseurl = link.split('/')
                    favicon = baseurl[0] + "//" + baseurl[2] + '/' + favicon
                } else {
                    favicon = "https://" + favicon
                }
            }
            image =
                BitmapFactory.decodeStream(URL(favicon).openConnection().getInputStream())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return image
    }

}