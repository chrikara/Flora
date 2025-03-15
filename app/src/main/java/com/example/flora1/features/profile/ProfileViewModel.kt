package com.example.flora1.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.presentation.viewmodel.BaseViewModel
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.domain.personaldetails.HeightValidator
import com.example.flora1.domain.personaldetails.WeightValidator
import com.example.flora1.features.main.EthereumWrapper
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences2: Preferences2,
    private val ethereumWrapper: EthereumWrapper,
    heightValidator: HeightValidator,
    weightValidator: WeightValidator,
) : BaseViewModel<ProfileState, ProfileAction, ProfileEvent>() {

    override var state by mutableStateOf(
        ProfileState(
            isHeightValid = heightValidator::isHeightValid,
            isWeightValid = weightValidator::isWeightValid,
        ),
    )
        private set

    init {
        combine(
            preferences2.isLoggedIn,
            preferences2.pregnancyStatus,
            preferences2.race,
            preferences2.contraceptiveMethods,
            preferences2.height,
            preferences2.weight,
            preferences2.averageCycleDays,
            preferences2.medVitsDescription,
            preferences2.hasTakenMedVits,
            preferences2.gyncosurgeryDescription,
            preferences2.hasDoneGynecosurgery,
            preferences2.isPredictionModeEnabled,
            preferences2.dateOfBirth,
            preferences2.theme,
            ethereumWrapper.isConnectedToMetamask.asFlow(),
        ) { values ->
            @Suppress("UNCHECKED_CAST")
            state = state.copy(
                isLoggedIn = values[0] as Boolean,
                pregnancyStatus = values[1] as PregnancyStatus,
                race = values[2] as Race,
                contraceptiveMethods = values[3] as List<ContraceptiveMethod>,
                height = values[4] as String,
                weight = values[5] as String,
                averageCycleDays = values[6] as Int,
                medVitsDescription = values[7] as String,
                hasTakenMedvits = values[8] as Boolean,
                gynosurgeryDescription = values[9] as String,
                hasDoneGynosurgery = values[10] as Boolean,
                isPredictionModeEnabled = values[11] as Boolean,
                dateOfBirth = values[12] as Long,
                theme = values[13] as Theme,
                isConnectedToMetamask = values[14] as Boolean,
            )
        }.launchIn(viewModelScope)
    }

    override fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            when (action) {
                ProfileAction.OnBackClicked -> {
                    send(ProfileEvent.NavigateBack)
                }

                ProfileAction.OnManageConsentClicked -> {
                    send(
                        when {
                            !state.isConnectedToMetamask -> ProfileEvent.MetamaskNotConnected
                            !state.isLoggedIn -> ProfileEvent.NavigateToLogin(id = MANAGE_DATA_CONSENT_ID)
                            else -> ProfileEvent.NavigateToManageConsent
                        }
                    )
                }

                ProfileAction.OnMyDoctorsClicked -> {
                    if (state.isConnectedToMetamask && state.isLoggedIn)
                        send(ProfileEvent.NavigateToMyDoctorsSuccess)
                    else
                        send(ProfileEvent.NavigateToMyDoctorsFailed)
                }

                is ProfileAction.OnChangeTheme -> {
                    preferences2.updateTheme(
                        when (state.theme) {
                            Theme.AUTO -> Theme.LIGHT
                            Theme.LIGHT -> Theme.DARK
                            Theme.DARK -> Theme.AUTO
                        }
                    )
                }

                ProfileAction.OnEnablePredictionModeClicked -> {
                    preferences2.saveIsPredictionModeEnabled(isPredictionModeEnabled = !state.isPredictionModeEnabled)

                }

                is ProfileAction.OnPregnancyStatButtonClicked -> {
                    preferences2.savePregnancyStatus(
                        action.pregnancyStatus ?: PregnancyStatus.NO_COMMENT
                    )

                }

                is ProfileAction.OnRaceButtonClicked -> {
                    preferences2.saveRace(
                        action.race ?: Race.NO_COMMENT,
                    )
                }

                is ProfileAction.OnContraceptiveMethodsButtonClicked -> {
                    preferences2.saveContraceptiveMethods(
                        action.methods,
                    )
                }

                is ProfileAction.OnHeightButtonClicked -> {
                    preferences2.saveHeight(
                        action.text.toFloatOrNull() ?: 0f
                    )
                }

                is ProfileAction.OnWeightButtonClicked -> {
                    preferences2.saveWeight(
                        action.text.toFloatOrNull() ?: 0f
                    )
                }

                is ProfileAction.OnAverageCycleDaysButtonClicked -> {
                    preferences2.saveAverageCycle(
                        action.day,
                    )
                }

                is ProfileAction.OnChangeMedvitsClicked -> {
                    launch {
                        preferences2.saveMedVitsDescription(action.description)
                    }
                    launch {
                        preferences2.saveHasTakenMedVits(action.enabled)
                    }
                }

                is ProfileAction.OnChangeGynosurgeryClicked -> {
                    launch {
                        preferences2.saveGyncosurgeryDescription(action.description)
                    }
                    launch {
                        preferences2.saveHasDoneGynecosurgery(action.enabled)
                    }
                }

                is ProfileAction.OnDateOfBirthButtonClicked -> {
                    preferences2.saveDateOfBirth(action.date)
                }

                ProfileAction.OnLoginClicked -> {
                    if (!state.isLoggedIn)
                        send(ProfileEvent.NavigateToLogin(id = LOGIN_ACTION_ID))
                }

                is ProfileAction.OnAcceptLogout -> {
                    preferences2.logout()
                    send(ProfileEvent.LogoutSuccessful)
                    action.onLogout()
                }

                ProfileAction.OnToggleMetamask -> {
                    if (state.isConnectedToMetamask)
                        ethereumWrapper.disconnect()
                    else
                        ethereumWrapper.connect()
                }
            }
        }
    }

    companion object {
        const val MANAGE_DATA_CONSENT_ID = "manageDataConsentId"
        const val LOGIN_ACTION_ID = "loginId"
    }
}
