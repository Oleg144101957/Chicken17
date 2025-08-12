package com.chicken.two.data

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.chicken.two.domain.AppsRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsRepositoryImpl @Inject constructor() : AppsRepository {

    override suspend fun provideFlyer(
        contextActivity: Context,
        appsDevKey: String
    ): MutableMap<String, Any?> =
        suspendCoroutine { continuation ->
            AppsFlyerLib.getInstance()
                .init(appsDevKey, FlyerListenner { continuation.resume(it) }, contextActivity)
                .start(contextActivity)
            AppsFlyerLib.getInstance().setDebugLog(true)
        }

    override suspend fun provideDIUU(contextActivity: Context): String = suspendCoroutine {
        val uuid = AppsFlyerLib.getInstance().getAppsFlyerUID(contextActivity).toString()
        it.resume(uuid)
    }

    private class FlyerListenner(private val onReceive: (MutableMap<String, Any?>) -> Unit) :
        AppsFlyerConversionListener {

        override fun onConversionDataSuccess(p0: MutableMap<String, Any?>) {
            onReceive(p0)
        }

        override fun onConversionDataFail(p0: String?) {
            val emptyMap: MutableMap<String, Any?> = mutableMapOf()
            emptyMap["onConversionDataFail"] = "null"
            onReceive(emptyMap)
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            val emptyMap: MutableMap<String, Any?> = mutableMapOf()
            emptyMap["onAppOpenAttribution"] = "null"
            onReceive(emptyMap)
        }

        override fun onAttributionFailure(p0: String?) {
            val emptyMap: MutableMap<String, Any?> = mutableMapOf()
            emptyMap["onAttributionFailure"] = "null"
            onReceive(emptyMap)
        }
    }
}

