package com.example.flora1.features.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
   private val preferences: Preferences,
) : ViewModel() {

    fun onSaveShouldNotShowOnBoarding(){
        preferences.saveShouldShowOnBoarding(shouldShowOnBoarding = false)
    }
}
