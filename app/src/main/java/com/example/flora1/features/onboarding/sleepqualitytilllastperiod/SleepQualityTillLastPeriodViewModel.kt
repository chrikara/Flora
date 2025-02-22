package com.example.flora1.features.onboarding.sleepqualitytilllastperiod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StressTillLastPeriodViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    private var _selectedSleepQuality: MutableStateFlow<SleepQuality> =
        MutableStateFlow(SleepQuality.GOOD)
    val selectedStressLevel: StateFlow<SleepQuality> = _selectedSleepQuality

    fun onSelectedSleepQualityChanged(sleepQuality: SleepQuality) {
        _selectedSleepQuality.value = sleepQuality
    }

    fun onSaveSleepQualityLevel(sleepQuality: SleepQuality) {
        viewModelScope.launch {
            preferences.saveSleepQualityTillLastPeriod(sleepQuality)
        }
    }
}

enum class SleepQuality(val text: String) {
    UNSATISFACTORY("Unsatisfactory"),
    MEDIOCRE("Mediocre"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXCELLENT("Excellent"),
    DONT_REMEMBER("Don't remember");

    companion object {
        fun fromString(text: String): SleepQuality {
            return when (text) {
                UNSATISFACTORY.text -> UNSATISFACTORY
                MEDIOCRE.text -> MEDIOCRE
                GOOD.text -> GOOD
                VERY_GOOD.text -> VERY_GOOD
                EXCELLENT.text -> EXCELLENT
                DONT_REMEMBER.text -> DONT_REMEMBER
                else -> DONT_REMEMBER // Default to "Don't remember" if no match
            }
        }
    }
}

