package com.ze.us.ga.m.util.web

import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.chicken.two.MainActivity
import com.chicken.two.data.ErrorMessage
import com.chicken.two.util.UrlBuilder
import com.chicken.two.util.web.CustomWebViewForChildren
import com.chicken.two.util.web.MainCustomWebView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WebViewClientForChildren(
    private val parentWebView: MainCustomWebView,
    private val webViewChild: CustomWebViewForChildren,
    private val activity: MainActivity
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        Log.d("123123", "onPageFinished for Children")

        val child: View? = parentWebView.getChildAt(0)

        if (child == null) {
            parentWebView.addView(webViewChild)
        }
        CookieManager.getInstance().flush()
    }

    override fun onReceivedError(
        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
    ) {
        if (request?.isForMainFrame == true) {
            Log.v("123123", "receiver Error")
            val phoneModel = "${Build.MANUFACTURER} ${Build.MODEL}"
            val currentTime =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val errorTxt = UrlBuilder("").addParameter("phoneModel", phoneModel)
                .addParameter("time", currentTime).addParameter("url", view?.url)
                .addParameter("error", error?.description.toString()).build()
            val errorMessage = ErrorMessage(message = errorTxt)
            activity.sendError(errorMessage)
        }

        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?
    ) {
        if (request?.isForMainFrame == true) {
            Log.v("123123", "receiver HTTP Error")
            val phoneModel = "${Build.MANUFACTURER} ${Build.MODEL}"
            val currentTime =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val errorTxt = UrlBuilder("").addParameter("phoneModel", phoneModel)
                .addParameter("time", currentTime).addParameter("url", view?.url)
                .addParameter("error", errorResponse?.reasonPhrase).build()
            val errorMessage = ErrorMessage(message = errorTxt)
            activity.sendError(errorMessage)
        }
        super.onReceivedHttpError(view, request, errorResponse)
    }
}