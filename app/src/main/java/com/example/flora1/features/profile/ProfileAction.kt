package com.example.flora1.features.profile

import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus

sealed interface ProfileAction {
    data object OnManageConsentClicked : ProfileAction
    data object OnBackClicked : ProfileAction
    data object OnMyDoctorsClicked : ProfileAction
    data object OnChangeTheme : ProfileAction
    data object OnEnablePredictionModeClicked : ProfileAction
    data class OnPregnancyStatButtonClicked(val pregnancyStatus: PregnancyStatus?) : ProfileAction
    data class OnRaceButtonClicked(val race: Race?) : ProfileAction
    data class OnContraceptiveMethodsButtonClicked(val methods: List<ContraceptiveMethod>) :
        ProfileAction

    data class OnHeightButtonClicked(val text: String) : ProfileAction
    data class OnWeightButtonClicked(val text: String) : ProfileAction
    data class OnAverageCycleDaysButtonClicked(val day: Int) : ProfileAction
    data class OnChangeMedvitsClicked(
        val enabled: Boolean,
        val description: String,
    ) : ProfileAction

    data class OnChangeGynosurgeryClicked(
        val enabled: Boolean,
        val description: String,
    ) : ProfileAction

    data class OnDateOfBirthButtonClicked(val date: Long) : ProfileAction
    data object OnLoginClicked : ProfileAction
    data class OnAcceptLogout(val onLogout: () -> Unit) : ProfileAction

}
