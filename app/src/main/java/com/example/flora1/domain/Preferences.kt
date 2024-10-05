package com.example.flora1.domain

interface Preferences {

    fun saveAverageCycle(averageCycleDays: Int)
    fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean)
    fun saveDateOfBirth(dateOfBirth: Long)
    fun saveUsername(username: String)
    fun saveHeight(height: Float)

    val username: String
    val height: Float
    val averageCycleDays: Int
    val shouldShowOnBoarding: Boolean
    val dateOfBirth: String
}
