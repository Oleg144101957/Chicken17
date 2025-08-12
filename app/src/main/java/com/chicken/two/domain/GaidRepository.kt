package com.chicken.two.domain

interface GaidRepository {

    suspend fun getGaid():String

}