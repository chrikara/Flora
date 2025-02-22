package com.example.flora1.features.onboarding.race

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    private var _selectedRace: MutableStateFlow<Race> = MutableStateFlow(Race.NO_COMMENT)
    val selectedRace: StateFlow<Race> = _selectedRace

    fun onSelectedRaceChanged(race: Race) {
        _selectedRace.value = race
    }

    fun onSaveRace(race: Race) {
        viewModelScope.launch {
            preferences.saveRace(race)
        }
    }
}

enum class Race(val text: String) {
    NO_COMMENT("No comment"),
    ASIAN("Asian"),
    AFRICAN("African"),
    WHITE("White"),
    HISPANIC("Hispanic"),
    OTHER("Other");

    companion object {
        fun fromString(text: String): Race = when (text) {
            "No comment" -> NO_COMMENT
            "Asian" -> ASIAN
            "African" -> AFRICAN
            "White" -> WHITE
            "Hispanic" -> HISPANIC
            "Other" -> OTHER
            else -> NO_COMMENT
        }
    }
}
