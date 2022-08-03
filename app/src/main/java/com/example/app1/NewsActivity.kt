package com.example.app1

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar?.title = intent.getStringExtra("TITLE")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnBlock = findViewById<ImageButton>(R.id.btn_block)
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
    }

    private fun showDialog(url: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.redirect))
            .setMessage(getString(R.string.askConfirm))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { d, whichButton ->
                    d.dismiss()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { d, whichButton ->
                    d.dismiss()
                    //onBackPressed()
                })
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}