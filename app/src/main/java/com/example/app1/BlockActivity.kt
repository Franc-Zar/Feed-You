package com.example.app1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class BlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layout = findViewById<LinearLayout>(R.id.lay_links)

        var content = ""
        try {
            val lang = getSharedPreferences(getString(R.string.lang), Context.MODE_PRIVATE).all[getString(R.string.lang)]
            val inputStream: InputStream = application.assets.open("feeds/"+lang+".json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            content = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val blockedLinks = getSharedPreferences(getString(R.string.blocked), Context.MODE_PRIVATE)

        if (content != ""){
            val json = JSONObject(content)
            val topics = resources.getStringArray(R.array.topics_it)
            for(i in 0 until topics.size) {
                val title = TextView(this)
                val color = resources.getIntArray(R.array.topics_colors)[i]
                title.setTextColor(color)
                title.setText(topics[i])
                layout.addView(title)
                for (k in json.keys()){
                    if (k.toInt() == i){
                        val topicLinks = json.getJSONArray(k)
                        for (j in 0 until topicLinks.length()) {
                            val cardView = CardView(this)
                            val content = View.inflate(baseContext, R.layout.topic_links_list, null)
                            val tv = content.findViewById<TextView>(R.id.tv_link)
                            tv.setText(topicLinks.getString(j))
                            val btn = content.findViewById<ImageButton>(R.id.btn_link)

                            for(link in blockedLinks.all){
                                if (link.value!! == topicLinks.getString(j)){
                                    tv.setTextColor(getColor(R.color.red))
                                    break
                                }
                            }

                            btn.setOnClickListener {
                                var blocked = false
                                for(link in blockedLinks.all) {
                                    if (link.value!! == topicLinks.getString(j)) {
                                        blocked = true
                                        tv.setTextColor(getColor(R.color.main))
                                        with(blockedLinks.edit()) {
                                            remove(link.key)
                                            apply()
                                        }
                                    }
                                }
                                if (!blocked){
                                    tv.setTextColor(getColor(R.color.red))
                                    with(blockedLinks.edit()) {
                                        putString(
                                            (blockedLinks.all.size + 1).toString(),
                                            topicLinks.getString(j)
                                        )
                                        apply()
                                    }
                                }
                            }

                            cardView.addView(content)
                            layout.addView(cardView)
                        }
                        break
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}