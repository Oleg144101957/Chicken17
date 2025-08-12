package com.chicken.two.domain

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float
}



