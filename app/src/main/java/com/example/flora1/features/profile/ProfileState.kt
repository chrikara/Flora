package com.example.flora1.features.profile

import com.example.flora1.domain.Theme
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus

data class ProfileState(
    val pregnancyStatus: PregnancyStatus = PregnancyStatus.NO_COMMENT,
    val isLoggedIn: Boolean = false,
    val race: Race = Race.NO_COMMENT,
    val contraceptiveMethods: List<ContraceptiveMethod> = emptyList(),
    val height: String = "150",
    val weight: String = "80",
    val averageCycleDays: Int = 0,
    val medVitsDescription: String = "",
    val hasTakenMedvits: Boolean = false,
    val gynosurgeryDescription: String = "",
    val hasDoneGynosurgery: Boolean = false,
    val isPredictionModeEnabled: Boolean = false,
    val dateOfBirth: Long = 10L,
    val theme: Theme = Theme.AUTO,
    val isConnectedToMetamask: Boolean = false,
    val isHeightValid: (String) -> Boolean = { true },
    val isWeightValid: (String) -> Boolean = { true }
)
