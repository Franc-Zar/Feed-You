package com.example.app1

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar?.title = intent.getStringExtra("TITLE")
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.loadUrl(intent.getStringExtra("HTML_CONTENT") ?: "https://www.google.com")
        //TODO: risontrollare il fallback nel caso in cui il link non è passato(oppure metterlo non nullable)
        //TODO Controllo del comportamento di WebView in assenza di rete o Url malformato...(alle volte link non ha http all'inizio...)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                showDialog()
                return false
            }
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.redirect))
            .setMessage(getString(R.string.askConfirm))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    dialog.dismiss()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(intent.getStringExtra("HTML_CONTENT"))
                    startActivity(i)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    dialog.dismiss()
                    onBackPressed()
                })
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}