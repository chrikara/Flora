package com.example.flora1.features.usernameage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsernameAgeViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private var _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    val enabled = _username.map { username ->
        username.isNotBlank() && username.length <= MAX_USERNAME_CHARS
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false,
    )

    fun onUsernameChange(username: String, onShowMessage: () -> Unit) {
        if (username.length <= MAX_USERNAME_CHARS)
            _username.value = username
        else
            onShowMessage()

    }

    fun onSaveUsername(username: String) {
        preferences.saveUsername(username)
    }

    companion object {
        const val MAX_USERNAME_CHARS = 15
    }
}

