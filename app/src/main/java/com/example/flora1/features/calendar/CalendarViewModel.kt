package com.example.flora1.features.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences
import com.example.flora1.domain.db.GetAllPeriodsUseCase
import com.example.flora1.domain.db.SavePeriodUseCase
import com.example.flora1.domain.db.model.Period
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllPeriodsUseCase: GetAllPeriodsUseCase,
    private val savePeriodUseCase: SavePeriodUseCase,
    private val preferences: Preferences,
) : ViewModel() {

    val periods = getAllPeriodsUseCase.getAllPeriods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet(),
        )

    fun onSavePeriod(period: Period) {
        viewModelScope.launch {
            savePeriodUseCase.savePeriod(period)
        }
    }
}
