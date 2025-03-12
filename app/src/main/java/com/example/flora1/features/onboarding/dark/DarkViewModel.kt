package com.example.flora1.features.onboarding.dark

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.flow.stateIn
import com.example.flora1.core.network.clients.WebSocketClient
import com.example.flora1.data.auth.RefreshService
import com.example.flora1.data.auth.UploadFloatsService
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.Theme
import com.example.flora1.domain.db.GetPeriodsForMonthUseCase
import com.example.flora1.domain.db.SavePeriodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DarkViewModel @Inject constructor(
    private val preferences: Preferences2,
): ViewModel() {
    val theme = preferences.theme
        .stateIn(this, Theme.AUTO)

    fun changeTheme(theme: Theme) {
        viewModelScope.launch {
            preferences.updateTheme(theme)
        }
    }
}
