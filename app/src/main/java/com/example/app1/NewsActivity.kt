package com.example.app1

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.utilities.config.Companion.domain
import com.example.app1.utilities.config.Companion.inviteLink
import com.example.app1.utilities.linkUtilities.Companion.generateContentLink
import com.google.android.material.appbar.MaterialToolbar
import com.example.app1.model.FeederPreferences
import java.lang.Thread

class NewsActivity : AppCompatActivity() {

    private fun shareNews() {

        val shareLink = generateContentLink(inviteLink, domain)
        val shareNews = Intent(Intent.ACTION_SEND)
        val shareContent = intent.getStringExtra("HTML_CONTENT")!!.toString() + "\n\nShared via FeedYou: " + shareLink

        shareNews.type = "text/plain"
        shareNews.putExtra(Intent.EXTRA_TITLE, "Share this News!")
        shareNews.putExtra(Intent.EXTRA_TEXT, shareContent)

        startActivity(Intent.createChooser(shareNews, "Share News"))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news, menu)
        menu?.findItem(R.id.shareButton)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.shareButton -> shareNews()

        }

        return super.onOptionsItemSelected(item)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar?.title = intent.getStringExtra("TITLE")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val btnBlock = findViewById<ImageButton>(R.id.change_password)
        btnBlock.setOnClickListener{
            val blockedLinks = getSharedPreferences(getString(R.string.blocked), Context.MODE_PRIVATE)
            with(blockedLinks.edit()) {
                putString(
                    (blockedLinks.all.size + 1).toString(),
                    intent.getStringExtra("FEED")!!
                )
                apply()
            }
            Toast.makeText(
                baseContext, getString(R.string.linkBlocked),
                Toast.LENGTH_LONG
            ).show()
        }

        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.mixedContentMode = MIXED_CONTENT_COMPATIBILITY_MODE
        webView.loadUrl(intent.getStringExtra("HTML_CONTENT")!!)
        //TODO: risontrollare il fallback nel caso in cui il link non è passato(oppure metterlo non nullable)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
                ): Boolean {
                webView.url?.let { showDialog(it) }
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (error?.errorCode == ERROR_CONNECT || error?.errorCode == ERROR_TIMEOUT ){
                    // TODO schermata errore personalizzata
                }
                super.onReceivedError(view, request, error)
            }
        }

        val thread = Thread {
            reading()
        }
        thread.start()
    }

    fun reading() {
        //Aggiorna le preferenze se leggo un articolo per almeno un tot di millisecondi(20.000)
        Thread.sleep(resources.getInteger(R.integer.readingTimer).toLong())
        if (! this.isDestroyed) {
            val feeder = FeederPreferences(baseContext)
            feeder.update(intent.getIntExtra("CATEGORY", -1))
        }
    }

    private fun showDialog(url: String) {

        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setView(layoutInflater.inflate(R.layout.alert_feed_you,null))

        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Cancel"
        ) {
                dialog, which -> dialog.dismiss()
        }

        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Ok"
        ) {
                dialog, which ->  dialog.dismiss()
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        alertDialog.show()

        val alertMessage = alertDialog.findViewById<TextView>(R.id.alertMessage)

        alertMessage?.setText(R.string.askConfirm)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}