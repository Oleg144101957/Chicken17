package com.chicken.two.data

import android.content.Context
import androidx.core.content.edit
import com.chicken.two.domain.DataStoreRepository
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    private val pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)


    override fun getUrl(): String {
        return pref.getString(GOAL, EMPTY) ?: EMPTY
    }

    override fun saveUrl(newGoalToSave: String) {
        pref.edit { putString(GOAL, newGoalToSave) }
    }

    override fun setSpeed(newSpeed: Float) {
        pref.edit { putFloat(SPEED, newSpeed) }
    }


    override fun getSpeed(): Float {
        return pref.getFloat(SPEED, 5f)
    }



    companion object {
        private const val SHARED_PREF_NAME = "USER STORAGE BY SHARED PREFERENCES"
        private const val GOAL = "GOAL"
        private const val EMPTY = "EMPTY"
        private const val SPEED = "speed"
    }
}


