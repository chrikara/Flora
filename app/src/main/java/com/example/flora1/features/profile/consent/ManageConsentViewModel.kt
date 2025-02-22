package com.example.flora1.features.profile.consent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageConsentViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    var isDataConsentGiven = preferences.hasGivenDataConsent
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            false,
        )
        private set

    fun onToggleDataConsent(newValue: Boolean) {
        viewModelScope.launch {
            preferences.saveHasGivenDataConsent(hasGivenDataConsent = newValue)
        }
    }
}
