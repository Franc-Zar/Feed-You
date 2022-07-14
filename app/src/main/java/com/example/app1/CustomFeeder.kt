package com.example.app1

import CustomAdapter
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ViewAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import org.json.JSONArray
import tw.ktrssreader.Reader
import tw.ktrssreader.kotlin.model.channel.RssStandardChannelData
import java.io.IOException
import java.io.InputStream
import java.net.URL

class CustomFeeder(var context: Context){

    protected fun getCatFeeds(cat:String): Array<String>? {
        var content: String? = ""
        try {
            val inputStream: InputStream = context.assets.open("feeds/"+cat+".xml")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            content = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var links: Array<String>? = null
        var json = JSONArray(content)
        for (i in 0 until json.length()) {
            var catData = json.getJSONObject(i)
            if(catData.get("cat").equals(cat)){
                var jsonLinks= catData.getJSONArray("links")
                for (j in 0 until jsonLinks.length()) {
                    links?.set(j, jsonLinks.getString(j))
                }
            }
        }
        return links
    }

     fun setNews(link: String, rv: RecyclerView, load: ProgressBar){
        var news = ArrayList<NewsData>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Reader.coRead<RssStandardChannelData>(link)
            result.items!!.forEach {
                news.add(NewsData(it.title!!, it.description!!, it.link!!, "", it.guid))
            }
            Log.d("KtRssReader", news.toString())
            withContext(Dispatchers.Main){
                val adapter = CustomAdapter(news)
                rv.adapter = adapter
                load.visibility= View.INVISIBLE
            }
        }
    }

}
