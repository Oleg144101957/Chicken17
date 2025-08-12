package com.chicken.two.data

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import com.chicken.two.domain.InstallReferrer
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class InstallReferrerImpl @Inject constructor(private val context: Context) : InstallReferrer {

    override suspend fun fetchReferrer(): String? = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            val referrerClient = InstallReferrerClient.newBuilder(context).build()

            referrerClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    val instReferrer = referrerClient.installReferrer.installReferrer

                    when (responseCode) {
                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                            try {
                                continuation.resume(instReferrer)
                            } catch (e: Exception) {
                                continuation.resumeWithException(e)
                            } finally {
                                referrerClient.endConnection()
                            }
                        }

                        InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                            continuation.resumeWithException(Exception("Install Referrer API не поддерживается."))
                        }

                        InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                            continuation.resumeWithException(Exception("Сервис Install Referrer недоступен."))
                        }

                        else -> {
                            continuation.resumeWithException(Exception("Неизвестная ошибка подключения: код $responseCode"))
                        }
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {
                    continuation.resumeWithException(Exception("Сервис Install Referrer отключен."))
                }
            })
        }
    }
}