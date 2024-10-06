package com.example.flora1.features.onboarding.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private var _weight = MutableStateFlow("60")
    val weight: StateFlow<String> = _weight
        .filter {weight ->
          //  (weight.matches(Regex("\\d+\\.?\\d*"))) This could have been done with Regex
           weight.hasAtMostOneDot() &&
                   weight.isOnlyDigitsMinusDots() &&
                   weight.hasLessThanMaxChars()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            _weight.value,
        )

    val enabled = weight.map { it.isNotBlank() && it.length > 1}
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false,
        )

    fun onWeightChanged(weight: String) {
        _weight.value = weight
    }

    fun onSaveWeight(weight: Float) {
        preferences.saveWeight(weight)
    }

    private fun String.hasAtMostOneDot() = count { it == '.' } <= 1
    private fun String.isOnlyDigitsMinusDots() = replace(".", "").all { it.isDigit() }
    private fun String.hasLessThanMaxChars() = length <= MAX_WEIGHT_CHARS

    companion object {
        const val MAX_WEIGHT_CHARS = 5
    }
}

