package com.example.flora1.features.onboarding.medvits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedVitsViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    private var _isTakingMedVits =
        MutableStateFlow(false)
    val isTakingMedVits: StateFlow<Boolean> = _isTakingMedVits

    private var _description =
        MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun onIsTakingMedVitsChanged(isTakingMedVits: Boolean) {
        _isTakingMedVits.value = isTakingMedVits
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onSaveHasTakenMedVits(hasTakenMedVits: Boolean) {
        viewModelScope.launch {
            preferences.saveHasTakenMedVits(hasTakenMedVits)
        }
    }

    fun onSaveMedVitsDescription(description: String) {
        viewModelScope.launch {
            preferences.saveMedVitsDescription(description)
        }
    }
}

