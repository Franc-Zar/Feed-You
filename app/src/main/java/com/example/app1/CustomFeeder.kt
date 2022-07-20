package com.example.app1

import CustomAdapter
import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.model.FeederPreferences
import com.example.app1.model.NewsData
import kotlinx.coroutines.*
import org.json.JSONObject
import tw.ktrssreader.Reader
import tw.ktrssreader.kotlin.model.channel.RssStandardChannelData
import java.io.IOException
import java.io.InputStream
import java.net.URL


class CustomFeeder(var context: Context){
    private val preferences = FeederPreferences(context)

    protected fun getLinksByTopic(): List<List<String>> {
        var content = ""
        try {
            val lang = context.getSharedPreferences(context.getString(R.string.lang), Context.MODE_PRIVATE).all[context.getString(R.string.lang)]
            val inputStream: InputStream = context.assets.open("feeds/"+lang+".json")
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
            result.items!!.forEach {
                news.add(NewsData(it.title!!, it.description ?: "...", it.link!!, 1, it.guid))
            }
            withContext(Dispatchers.Main){
                val adapter = CustomAdapter(news)
                rv.adapter = adapter
                load.visibility= View.INVISIBLE
            }
        }
    }

    fun setFeed(rv: RecyclerView, load: ProgressBar) {
        val news = mutableListOf<MutableList<NewsData>>()
        val links = getLinksByTopic()

        CoroutineScope(Dispatchers.IO).launch {
            for (i in links.indices){
                news.add(mutableListOf())
                for (j in 0 until links[i].size){
                    try {
                        val result = Reader.coRead<RssStandardChannelData>(links[i][j])
                        result.items!!.forEach {
                            news[i].add(NewsData(it.title!!,it.description ?: "...",
                                        it.link!!, i, it.guid))}
                    }catch (e : Exception){
                        ;
                    }
                }
                news[i].shuffle()
            }

            withContext(Dispatchers.Main) {
                val adapter = CustomAdapter(shuffler(news))
                rv.adapter = adapter
                load.visibility = View.INVISIBLE
            }
        }
    }

    fun shuffler(news: List<MutableList<NewsData>>): List<NewsData> {
        val list = mutableListOf<NewsData>()
        for (i in 0 until 100){
            val topic = preferences.getRandTopic()
            if (news[topic].isNotEmpty()){
                list.add(news[topic].removeFirst())
            }
        }
        return list
    }
}
