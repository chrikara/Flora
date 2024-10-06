package com.example.flora1.navigationroot

import androidx.lifecycle.ViewModel
import com.example.flora1.domain.Preferences
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationRootViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {
    val hasBeenPregnant = preferences.pregnancyStatus == PregnancyStatus.PREGNANT
}
