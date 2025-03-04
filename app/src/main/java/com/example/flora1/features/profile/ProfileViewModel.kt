package com.example.flora1.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.flow.stateIn
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.domain.personaldetails.HeightValidator
import com.example.flora1.domain.personaldetails.WeightValidator
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import com.example.flora1.features.profile.consent.ProfileEvent
import com.example.flora1.navigationroot.main.databaseRef
import com.example.flora1.navigationroot.main.incrementClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences2: Preferences2,
    internal val heightValidator: HeightValidator,
    internal val weightValidator: WeightValidator,
) : ViewModel() {

    val isLoggedIn = preferences2.isLoggedIn
        .stateIn(this, false)

    val pregnancyStatus = preferences2.pregnancyStatus
        .stateIn(this, PregnancyStatus.NOT_PREGNANT)

    val race = preferences2.race
        .stateIn(this, Race.NO_COMMENT)

    val contraceptiveMethods = preferences2.contraceptiveMethods
        .stateIn(this, emptyList())

    val height = preferences2.height
        .stateIn(this, "150")

    val weight = preferences2.weight
        .stateIn(this, "80")

    val averageCycleDays = preferences2.averageCycleDays
        .stateIn(this, 0)

    val medVitsDescription = preferences2.medVitsDescription
        .stateIn(this, "")

    val hasTakenMedvits = preferences2.hasTakenMedVits
        .stateIn(this, false)

    val gynosurgeryDescription = preferences2.gyncosurgeryDescription
        .stateIn(this, "")

    val hasDoneGynosurgery = preferences2.hasDoneGynecosurgery
        .stateIn(this, false)

    val isPredictionModeEnabled = preferences2.isPredictionModeEnabled
        .stateIn(this, false)

    val dateOfBirth = preferences2.dateOfBirth
        .stateIn(this, 10L)

    val theme = preferences2.theme
        .stateIn(this, Theme.AUTO)

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            databaseRef.incrementClick("profileAction ${action}")
            when (action) {
                ProfileAction.OnBackClicked -> {
                    _events.send(ProfileEvent.NavigateBack)
                }

                ProfileAction.OnManageConsentClicked -> {
                    if (isLoggedIn.value)
                        _events.send(ProfileEvent.NavigateToManageConsent)
                    else
                        _events.send(
                            ProfileEvent.NavigateToLogin(
                                id = MANAGE_DATA_CONSENT_ID,
                            )
                        )
                }

                ProfileAction.OnMyDoctorsClicked -> {
                    if (preferences2.hasGivenDataConsent.firstOrNull() == true && isLoggedIn.value)
                        _events.send(ProfileEvent.NavigateToMyDoctorsSuccess)
                    else
                        _events.send(ProfileEvent.NavigateToMyDoctorsFailed)
                }

                is ProfileAction.OnChangeTheme -> {
                    preferences2.updateTheme(
                        when (theme.value) {
                            Theme.AUTO -> Theme.LIGHT
                            Theme.LIGHT -> Theme.DARK
                            Theme.DARK -> Theme.AUTO
                        }
                    )
                }

                ProfileAction.OnEnablePredictionModeClicked -> {
                    viewModelScope.launch {
                        preferences2.saveIsPredictionModeEnabled(isPredictionModeEnabled = !isPredictionModeEnabled.value)
                    }
                }

                is ProfileAction.OnPregnancyStatButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.savePregnancyStatus(
                            action.pregnancyStatus ?: PregnancyStatus.NO_COMMENT
                        )
                    }
                }

                is ProfileAction.OnRaceButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveRace(
                            action.race ?: Race.NO_COMMENT,
                        )
                    }
                }

                is ProfileAction.OnContraceptiveMethodsButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveContraceptiveMethods(
                            action.methods,
                        )
                    }
                }

                is ProfileAction.OnHeightButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveHeight(
                            action.text.toFloatOrNull() ?: 0f
                        )
                    }
                }

                is ProfileAction.OnWeightButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveWeight(
                            action.text.toFloatOrNull() ?: 0f
                        )
                    }
                }

                is ProfileAction.OnAverageCycleDaysButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveAverageCycle(
                            action.day,
                        )
                    }
                }

                is ProfileAction.OnChangeMedvitsClicked ->
                    viewModelScope.launch {
                        launch {
                            preferences2.saveMedVitsDescription(action.description)
                        }
                        launch {
                            preferences2.saveHasTakenMedVits(action.enabled)
                        }
                    }

                is ProfileAction.OnChangeGynosurgeryClicked -> {
                    viewModelScope.launch {
                        launch {
                            preferences2.saveGyncosurgeryDescription(action.description)
                        }
                        launch {
                            preferences2.saveHasDoneGynecosurgery(action.enabled)
                        }
                    }
                }

                is ProfileAction.OnDateOfBirthButtonClicked -> {
                    viewModelScope.launch {
                        preferences2.saveDateOfBirth(action.date)
                    }
                }

                ProfileAction.OnLoginClicked -> {
                    if (!isLoggedIn.value)
                        viewModelScope.launch {
                            _events.send(ProfileEvent.NavigateToLogin(id = LOGIN_ACTION_ID))
                        }
                }

                is ProfileAction.OnAcceptLogout -> {
                    viewModelScope.launch {
                        preferences2.logout()
                        _events.send(ProfileEvent.LogoutSuccessful)
                        action.onLogout()
                    }
                }

                ProfileAction.OnDuthStats -> {
                    viewModelScope.launch {
                        _events.send(ProfileEvent.NavigateToDuthStats)
                    }
                }
            }
        }
    }

    companion object {
        const val MANAGE_DATA_CONSENT_ID = "manageDataConsentId"
        const val LOGIN_ACTION_ID = "loginId"
    }
}
