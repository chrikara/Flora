package com.example.flora1.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.domain.personaldetails.HeightValidator
import com.example.flora1.domain.personaldetails.WeightValidator
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import com.example.flora1.features.profile.consent.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences2: Preferences2,
    internal val heightValidator: HeightValidator,
    internal val weightValidator: WeightValidator,
) : ViewModel() {

    val isLoggedIn = preferences2.isLoggedIn
        .stateIn(false)

    val pregnancyStatus = preferences2.pregnancyStatus
        .stateIn(PregnancyStatus.NOT_PREGNANT)

    val race = preferences2.race
        .stateIn(Race.NO_COMMENT)

    val contraceptiveMethods = preferences2.contraceptiveMethods
        .stateIn(emptyList())

    val height = preferences2.height
        .stateIn("150")

    val weight = preferences2.weight
        .stateIn("80")

    val isPredictionModeEnabled = preferences2.isPredictionModeEnabled
        .stateIn(false)

    val theme = preferences2.theme
        .stateIn(Theme.AUTO)

    private fun <T> Flow<T>.stateIn(
        initialValue: T
    ): StateFlow<T> = stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), initialValue)

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            when (action) {
                ProfileAction.OnBackClicked -> {
                    _events.send(ProfileEvent.NavigateBack)
                }

                ProfileAction.OnManageConsentClicked -> {
                    _events.send(ProfileEvent.NavigateToManageConsent)
                }

                ProfileAction.OnMyDoctorsClicked -> {
                    if (preferences2.hasGivenDataConsent.firstOrNull() == true)
                        _events.send(ProfileEvent.NavigateToMyDoctors)
                    else
                        _events.send(ProfileEvent.ShowMessage("You have to give consent in Manage Data Consent section."))
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
            }
        }
    }
}
