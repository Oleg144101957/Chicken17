package com.chicken.two.util.web

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import com.chicken.two.MainActivity
import com.chicken.two.data.ErrorMessage
import com.chicken.two.util.UrlBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainWebViewClient(
    private val activity: MainActivity,
    private val onWhite: () -> Unit,
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("123123", "Main WebView Parent URL is $url")
        url ?: return
        CookieManager.getInstance().flush()
        if (url.contains("bHxh9yCK")) {
            onWhite.invoke()
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        Log.d("123123", "Should shouldOverrideUrlLoading ${request?.url}")

        // For diia
        if (request?.url.toString().contains("diia.app")) {
            try {
                val intentDiia = Intent(Intent.ACTION_VIEW)
                val dataForIntent = request?.url.toString().replaceFirst("https://", "diia://")
                intentDiia.setData(dataForIntent.toUri())
                activity.startActivity(intentDiia)
            } catch (e: Exception) {

            }
        } else {
            val url = request?.url.toString()
            return if (url.startsWith("http") || url.startsWith("https")) {
                false
            } else {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view?.context?.startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
                true
            }

        }

        return super.shouldOverrideUrlLoading(view, request)
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