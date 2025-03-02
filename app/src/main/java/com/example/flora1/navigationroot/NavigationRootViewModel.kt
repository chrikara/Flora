package com.example.flora1.navigationroot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationRootViewModel @Inject constructor(
    preferences2: Preferences2,
) : ViewModel() {

    val isLoggedIn = preferences2.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false,
        )
}
