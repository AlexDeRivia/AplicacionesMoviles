package com.example.s08_sanpedrito

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        val webView = findViewById<WebView>(R.id.webViewTerms)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Puedes cargar un HTML local o una URL externa:
        webView.loadUrl("file:///android_asset/terminos.html")
        // O desde una URL real: webView.loadUrl("https://uns.edu.pe/terminos")
    }
}
