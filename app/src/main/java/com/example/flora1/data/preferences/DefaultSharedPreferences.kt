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

    override fun saveAge(age: Int) {
        sharedPref.edit().putInt(KEY_USERNAME, age).apply()
    }

    override val username: String get() = sharedPref.getString(KEY_USERNAME, "") ?: ""
    override val age: Int get() = sharedPref.getInt(KEY_AGE, 0)

    override val averageCycleDays get() = sharedPref.getInt(KEY_AVERAGE_CYCLE, 0)
    override val shouldShowOnBoarding get() = sharedPref.getBoolean(KEY_SHOULD_SHOW_ONBOARDING, true)
    override val dateOfBirth get() = sharedPref.getString(KEY_DATE_OF_BIRTH, "") ?: ""

    companion object{
        private const val KEY_USERNAME = "username"
        private const val KEY_AGE = "age"
        private const val KEY_AVERAGE_CYCLE = "averageCycleDays"
        private const val KEY_SHOULD_SHOW_ONBOARDING = "shouldShowOnBoarding"
        private const val KEY_DATE_OF_BIRTH = "dateOfBirth"
    }
}
