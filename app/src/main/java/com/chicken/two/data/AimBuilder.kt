package com.chicken.two.data

import android.net.Uri

class AimBuilder {

    private var brinID: String = ""
    private var appsIdentif = ""
    private var appsData: MutableMap<String, Any?> = mutableMapOf()
    private var installReferrer = ""

    fun setBrinID(inputBrinId: String) = apply { this.brinID = inputBrinId }
    fun setAppsIdentif(inputAppsIdentif: String) = apply { this.appsIdentif = inputAppsIdentif }
    fun setAppsData(inputAppsData: MutableMap<String, Any?>) =
        apply { this.appsData = inputAppsData }

    fun setInstallReferrer(referrer: String) = apply { this.installReferrer = referrer }


    fun build(): String {
        val sb = StringBuilder()
        listOfDomain.forEach { sb.append(it) }
        val urlBuilder = Uri
            .parse(sb.toString())
            .buildUpon()

        urlBuilder.appendQueryParameter(APPSFLYER_ID_KEY, appsIdentif)
        urlBuilder.appendQueryParameter(GAD_ID_KEY, brinID)
        urlBuilder.appendQueryParameter(REFERRER, installReferrer)

        appendQueryParamForApps(appsData, urlBuilder)

        return urlBuilder.build().toString()
    }

    private fun appendQueryParamForApps(
        rawAppsData: MutableMap<String, Any?>,
        uriBuilder: Uri.Builder
    ) {
        rawAppsData.forEach { (key, value) ->
            if (value != null && value.toString().isNotEmpty()) {
                uriBuilder.appendQueryParameter(key, value.toString())
            }
        }
    }

    companion object {
        val listOfDomain = listOf("ht", "tps", "://", "blackevo", ".digital/bHxh9yCK")
        private const val APPSFLYER_ID_KEY = "appsflyerId"
        private const val GAD_ID_KEY = "gadid"
        private const val REFERRER = "reffer"
    }
}