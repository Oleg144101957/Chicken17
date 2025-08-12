package com.chicken.two.domain

import com.chicken.two.data.ErrorMessage


interface PostErrorRepository {

    suspend fun postError(message: ErrorMessage)

}