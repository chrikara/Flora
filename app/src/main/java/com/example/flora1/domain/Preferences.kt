package com.example.flora1.domain

interface Preferences {

    fun saveAverageCycle(averageCycleDays: Int)
    fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean)
    fun saveDateOfBirth(dateOfBirth: Long)
    fun saveUsername(username: String)
    fun saveAge(age: Int)

    val username: String
    val age: Int
    val averageCycleDays: Int
    val shouldShowOnBoarding: Boolean
    val dateOfBirth: String
}
