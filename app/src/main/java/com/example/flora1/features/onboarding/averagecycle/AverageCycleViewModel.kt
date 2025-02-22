package com.example.flora1.features.onboarding.averagecycle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AverageCycleViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    var selectedNumber by mutableStateOf<Int?>(null)
        private set

    fun onSaveAverageCycleDays(averageCycleDays: Int) {
        viewModelScope.launch {
            preferences.saveAverageCycle(averageCycleDays)
        }
    }

    fun onSelectedNumberChange(number: Int?) {
        selectedNumber = number
    }
}

