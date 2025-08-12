package com.chicken.two.domain

import android.content.Context

interface PushServiceInitializer {

    fun initializePushService(advertID: String, context: Context)

}