package com.example.flora1.features.onboarding.height

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.personaldetails.HeightValidator
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
class HeightViewModel @Inject constructor(
    private val preferences: Preferences2,
    private val heightValidator: HeightValidator,
) : ViewModel() {

    private var _height = MutableStateFlow(TextFieldValue("150"))
    val height: StateFlow<TextFieldValue> = _height
        .filter { height -> heightValidator.isHeightValid(height.text) }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            _height.value,
        )

    val enabled = height.map { it.text.isNotBlank() && it.text.length > 1 }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false,
        )

    fun onHeightChanged(height: TextFieldValue) {
        _height.value = height
    }

    fun onSaveHeight(height: Float) {
        viewModelScope.launch {
            preferences.saveHeight(height)
        }
    }

    companion object {
        const val MAX_USERNAME_CHARS = 15
    }
}

