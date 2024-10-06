package com.example.flora1.domain

import com.example.flora1.features.onboarding.weight.PregnancyStatus

interface Preferences {

    fun saveAverageCycle(averageCycleDays: Int)
    fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean)
    fun saveDateOfBirth(dateOfBirth: Long)
    fun saveUsername(username: String)
    fun saveHeight(height: Float)
    fun saveWeight(weight: Float)
    fun savePregnancyStatus(pregnancyStatus: PregnancyStatus)

    val username: String
    val height: Float
    val weight: Float
    val pregnancyStatus: PregnancyStatus
    val averageCycleDays: Int
    val shouldShowOnBoarding: Boolean
    val dateOfBirth: String
}
