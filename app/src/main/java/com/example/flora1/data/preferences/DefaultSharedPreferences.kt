package com.example.flora1.data.preferences

import android.content.SharedPreferences
import com.example.flora1.domain.Preferences

class DefaultSharedPreferences(
    private val sharedPref : SharedPreferences

) : Preferences {
    override fun saveAverageCycle(averageCycleDays: Int) {
        sharedPref.edit().putInt(KEY_AVERAGE_CYCLE, averageCycleDays).apply()
    }

    override fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean) {
        sharedPref.edit().putBoolean(KEY_SHOULD_SHOW_ONBOARDING, shouldShowOnBoarding).apply()
    }

    override fun saveDateOfBirth(dateOfBirth: Long) {
        sharedPref.edit().putLong(KEY_DATE_OF_BIRTH, dateOfBirth).apply()
    }

    override fun saveUsername(username: String) {
        sharedPref.edit().putString(KEY_USERNAME, username).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit().putFloat(KEY_WEIGHT, weight).apply()
    }

    override fun saveHeight(height: Float) {
        sharedPref.edit().putFloat(KEY_HEIGHT, height).apply()
    }

    override val height: Float get() = sharedPref.getFloat(KEY_HEIGHT, 0f)
    override val username: String get() = sharedPref.getString(KEY_USERNAME, "") ?: ""
    override val weight: Float get() = sharedPref.getFloat(KEY_WEIGHT, 0f)
    override val averageCycleDays get() = sharedPref.getInt(KEY_AVERAGE_CYCLE, 0)
    override val shouldShowOnBoarding get() = sharedPref.getBoolean(KEY_SHOULD_SHOW_ONBOARDING, true)
    override val dateOfBirth get() = sharedPref.getString(KEY_DATE_OF_BIRTH, "") ?: ""

    companion object{
        private const val KEY_USERNAME = "username"
        private const val KEY_HEIGHT = "height"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_AVERAGE_CYCLE = "averageCycleDays"
        private const val KEY_SHOULD_SHOW_ONBOARDING = "shouldShowOnBoarding"
        private const val KEY_DATE_OF_BIRTH = "dateOfBirth"
    }
}
