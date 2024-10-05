package com.example.flora1.features.onboarding.height

import androidx.core.text.isDigitsOnly
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
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private var _height = MutableStateFlow("150")
    val height: StateFlow<String> = _height
        .filter {height ->
            height.isDigitsOnly() && height.length < 4
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            _height.value,
        )

    val enabled = height.map { it.isNotBlank() && it.length > 1}
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false,
        )

    fun onHeightChanged(height: String) {
            _height.value = height
    }

    fun onSaveHeight(height: Float) {
        preferences.saveHeight(height)
    }

    companion object {
        const val MAX_USERNAME_CHARS = 15
    }
}

