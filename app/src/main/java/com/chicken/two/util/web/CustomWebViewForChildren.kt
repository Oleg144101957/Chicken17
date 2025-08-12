package com.chicken.two.util.web

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import com.chicken.two.MainActivity
import com.ze.us.ga.m.util.web.WebViewClientForChildren

@SuppressLint("SetJavaScriptEnabled")
class CustomWebViewForChildren(
    context: MainActivity, parentWebView: MainCustomWebView
) : WebView(context) {

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = WebViewClientForChildren(parentWebView, this, context)

        with(settings) {
            databaseEnabled = true
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            allowContentAccess = true
            allowFileAccess = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(true)
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            defaultTextEncodingName = "UTF-8"
            cacheMode = WebSettings.LOAD_DEFAULT
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            userAgentString = userAgentString.agentChanger()
        }

        setLayerType(LAYER_TYPE_HARDWARE, null)

        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false

        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
    }

    private fun String.agentChanger(): String {
        return this.replace("wv", " ")
    }
}
