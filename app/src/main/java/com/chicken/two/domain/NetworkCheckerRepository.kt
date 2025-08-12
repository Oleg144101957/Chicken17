package com.chicken.two.domain

import android.content.Context

interface NetworkCheckerRepository {
    fun isConnectionAvailable(): Boolean
    fun isAdbEnabled(context: Context): Boolean
}