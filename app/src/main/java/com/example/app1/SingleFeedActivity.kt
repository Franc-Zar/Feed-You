package com.example.app1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException
import java.io.InputStream

class SingleFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_feed)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPref = getSharedPreferences(getString(R.string.feedLink), Context.MODE_PRIVATE)
        val arrayTV = mutableListOf<TextView>()

        val btn_reset = findViewById<Button>(R.id.btn_reset)
        btn_reset.setOnClickListener {
            for(text in arrayTV){
                text.setTextColor(getColor(R.color.main))
            }
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            Toast.makeText(
                baseContext, getString(R.string.toMultiFeed),
                Toast.LENGTH_SHORT
            ).show()
        }

        val layout = findViewById<LinearLayout>(R.id.lay_links)
        var content = ""
        try {
            val lang = getSharedPreferences(
                getString(R.string.lang),
                Context.MODE_PRIVATE
            ).all[getString(R.string.lang)]
            val inputStream: InputStream = application.assets.open("feeds/" + lang + ".json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            content = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val link = sharedPref.all

        if (content != "") {
            val json = JSONObject(content)
            val topics = resources.getStringArray(R.array.topics_it)
            for (i in 0 until topics.size) {
                val title = TextView(this)
                val color = resources.getIntArray(R.array.topics_colors)[i]
                title.setTextColor(color)
                title.setText(topics[i])
                layout.addView(title)
                for (k in json.keys()) {
                    if (k.toInt() == i) {
                        val topicLinks = json.getJSONArray(k)
                        for (j in 0 until topicLinks.length()) {
                            val cardView = CardView(this)
                            val cardContent =
                                View.inflate(baseContext, R.layout.topic_links_list, null)
                            val tv = cardContent.findViewById<TextView>(R.id.tv_link)
                            arrayTV.add(tv)
                            tv.setText(topicLinks.getString(j))
                            val btn = cardContent.findViewById<ImageButton>(R.id.btn_link)

                            if (link.isNotEmpty()){
                                if(link[getString(R.string.feedLink)] == topicLinks.getString(j)) {
                                    tv.setTextColor(getColor(R.color.red))
                                }
                            }

                            btn.setOnClickListener {
                                if (link.isNotEmpty()) {
                                    if (link[getString(R.string.feedLink)] == topicLinks.getString(j)) {
                                        tv.setTextColor(getColor(R.color.main))
                                        with(sharedPref.edit()) {
                                            clear()
                                            apply()
                                        }
                                        Toast.makeText(
                                            baseContext, getString(R.string.toMultiFeed),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@setOnClickListener
                                    }
                                }
                                for (text in arrayTV){
                                    text.setTextColor(getColor(R.color.main))
                                }
                                tv.setTextColor(getColor(R.color.red))
                                with(sharedPref.edit()){
                                    clear()
                                    putString(getString(R.string.feedLink), topicLinks.getString(j))
                                    apply()
                                }
                                Toast.makeText(
                                    baseContext, getString(R.string.toSingleFeed),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            cardView.addView(cardContent)
                            layout.addView(cardView)
                        }
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