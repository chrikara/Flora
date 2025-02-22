package com.example.flora1.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.features.profile.consent.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences2: Preferences2,
) : ViewModel() {

    val isLoggedIn = preferences2.isLoggedIn
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            false,
        )

    val isPredictionModeEnabled = preferences2.isPredictionModeEnabled
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            false,
        )

    val theme = preferences2.theme
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            Theme.AUTO,
        )

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
            }
        }
    }

}
