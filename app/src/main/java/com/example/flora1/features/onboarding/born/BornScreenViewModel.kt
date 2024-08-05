package com.example.flora1.features.onboarding.born

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BornScreenViewModel @Inject constructor(
    private val preferences: Preferences,
) :ViewModel(){

    fun onSaveDateOfBirth(dateOfBirth : Long){
        preferences.saveDateOfBirth(dateOfBirth)
    }
}

