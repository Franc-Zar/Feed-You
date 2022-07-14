package com.example.app1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toolbar

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        var webView = findViewById<WebView>(R.id.web_view)
        webView.loadUrl(intent.getStringExtra("HTML_CONTENT") ?: "https://www.google.com")

        //TODO: risontrollare il fallback nel caso in cui il link non è passato(oppure metterlo non nullable)
        //TODO Controllo del comportamento di WebView in assenza di rete o Url malformato...(alle volte link non ha http all'inizio...)

    }
}