package com.example.flora1.features.onboarding.born

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BornScreenViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    fun onSaveDateOfBirth(dateOfBirth: Long) {
        viewModelScope.launch {
            preferences.saveDateOfBirth(dateOfBirth)
        }
    }

    companion object {
        const val MIN_ELIGIBLE_AGE_TO_USE_FLORA = 13
    }
}

