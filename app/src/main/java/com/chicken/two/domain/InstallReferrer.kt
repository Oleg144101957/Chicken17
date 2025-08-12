package com.chicken.two.domain

interface InstallReferrer {

    suspend fun fetchReferrer(): String?
}
