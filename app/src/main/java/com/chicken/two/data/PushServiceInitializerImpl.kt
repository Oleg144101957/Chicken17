package com.chicken.two.data

import android.content.Context
import com.chicken.two.domain.PushServiceInitializer
import com.onesignal.OneSignal
import javax.inject.Inject

class PushServiceInitializerImpl @Inject constructor() :
    PushServiceInitializer {

    override fun initializePushService(advertID: String, context: Context) {
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ONE_SIGNAL_DEV_KEY)
        OneSignal.setExternalUserId(advertID)
        OneSignal.promptForPushNotifications(true)
    }

    companion object {
        private const val ONE_SIGNAL_DEV_KEY = "1722f25e-ef21-4057-8177-9b1762a8c1b5"
    }
}