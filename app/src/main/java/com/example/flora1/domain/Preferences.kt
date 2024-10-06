package com.example.flora1.domain

import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.NumericalOptions
import com.example.flora1.features.onboarding.weight.PregnancyStatus

interface Preferences {

    fun saveAverageCycle(averageCycleDays: Int)
    fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean)
    fun saveDateOfBirth(dateOfBirth: Long)
    fun saveUsername(username: String)
    fun saveHeight(height: Float)
    fun saveWeight(weight: Float)
    fun savePregnancyStatus(pregnancyStatus: PregnancyStatus)
    fun saveTotalPregnancies(number: NumericalOptions)
    fun saveTotalMiscarriages(number: NumericalOptions)
    fun saveTotalAbortions(number: NumericalOptions)
    fun saveRace(race: Race)
    fun saveHasTakenMedVits(hasTakenMedVits: Boolean)
    fun saveMedVitsDescription(description: String)
    fun saveHasDoneGynecosurgery(hasDoneGynecosurgery: Boolean)
    fun saveGyncosurgeryDescription(description: String)

    val username: String
    val height: Float
    val weight: Float
    val pregnancyStatus: PregnancyStatus
    val totalPregnancies: NumericalOptions?
    val totalMiscarriages: NumericalOptions?
    val totalAbortions: NumericalOptions?
    val race: Race
    val hasTakenMedVits: Boolean
    val medVitsDescription: String
    val hasDoneGynecosurgery: Boolean
    val gyncosurgeryDescription: String
    val averageCycleDays: Int
    val shouldShowOnBoarding: Boolean
    val dateOfBirth: String
}
