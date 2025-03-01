package com.example.flora1.features.onboarding.weight

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.personaldetails.WeightValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences2,
    private val weightValidator: WeightValidator,
) : ViewModel() {

    private var _weight = MutableStateFlow(TextFieldValue("60"))
    val weight: StateFlow<TextFieldValue> = _weight
        .filter { weight -> weightValidator.isWeightValid(weight.text) }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            _weight.value,
        )

    val enabled = weight.map { it.text.isNotBlank() && it.text.length > 1 }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false,
        )

    fun onWeightChanged(weight: TextFieldValue) {
        _weight.value = weight
    }

    fun onSaveWeight(weight: Float) {
        viewModelScope.launch {
            preferences.saveWeight(weight)
        }
    }

    companion object {
        const val MAX_WEIGHT_CHARS = 4
    }
}

