package com.chicken.two.util.web

import android.net.Uri
import android.os.Message
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.chicken.two.MainActivity
import com.ze.us.ga.m.util.web.WebViewClientForChildren

class MainWebChromeClient(
    private val content: ActivityResultLauncher<String>,
    private val context1: MainActivity,
    private val parentWebView: MainCustomWebView
) : WebChromeClient() {

    private var filePathCallback: ValueCallback<Array<Uri?>>? = null


    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri?>>,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        this.filePathCallback = filePathCallback
        content.launch("image/*")
        return true
    }

    fun onFileCallback(uris: Array<Uri?>) {
        filePathCallback?.onReceiveValue(uris)
        filePathCallback = null
    }


    override fun onCreateWindow(
        view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
    ): Boolean {
        Log.d("123123", "onCreateWindow $resultMsg")

        val childWebView = CustomWebViewForChildren(context1, parentWebView)

        context1.currentChildWebView = childWebView

        childWebView.webViewClient = WebViewClientForChildren(
            parentWebView = parentWebView,
            webViewChild = childWebView, context1
        )

        val newWebChromeClientForChild = object : WebChromeClient() {
            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                parentWebView.removeView(window)
                if (context1.currentChildWebView == window) {
                    context1.currentChildWebView = null
                }
            }
        }

        childWebView.webChromeClient = newWebChromeClientForChild
        parentWebView.addView(childWebView)

        val transport = resultMsg?.obj as WebView.WebViewTransport
        transport.webView = childWebView
        resultMsg.sendToTarget()

        return true
    }


}


