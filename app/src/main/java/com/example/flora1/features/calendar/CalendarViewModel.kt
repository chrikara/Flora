package com.example.flora1.features.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.db.DeletePeriodUseCase
import com.example.flora1.domain.db.GetAllPeriodsUseCase
import com.example.flora1.domain.db.SavePeriodUseCase
import com.example.flora1.domain.db.model.Period
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllPeriodsUseCase: GetAllPeriodsUseCase,
    private val savePeriodUseCase: SavePeriodUseCase,
    private val deletePeriodUseCase: DeletePeriodUseCase,
) : ViewModel() {

    private val _events = Channel<CalendarEvent>()
    val events = _events.receiveAsFlow()

    val periods = getAllPeriodsUseCase.getAllPeriods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet(),
        )

    private var _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    private var _temporarySelectedPeriodDates = MutableStateFlow(emptySet<Period>())
    val temporarySelectedPeriodDates = _temporarySelectedPeriodDates.asStateFlow()

    fun edit() {
        _temporarySelectedPeriodDates.update { periods.value }
        _isEditing.update { true }
    }

    fun cancel() {
        _temporarySelectedPeriodDates.update { periods.value }
        _isEditing.update { false }
    }

    fun onTemporaryPeriodSelected(selectedPeriod: Period) {
        _temporarySelectedPeriodDates.update { temporaryPeriods ->
            if (selectedPeriod in temporaryPeriods)
                temporaryPeriods - selectedPeriod
            else
                temporaryPeriods + selectedPeriod
        }
    }

    fun save() {
        if (temporarySelectedPeriodDates.value == periods.value) {
            _isEditing.update { false }
            return
        }

        viewModelScope.launch {
            (temporarySelectedPeriodDates.value - periods.value).map { period ->
                launch {
                    savePeriod(period)
                }
            }

            (periods.value - temporarySelectedPeriodDates.value).map { period ->
                launch {
                    deletePeriod(period)
                }
            }

            _isEditing.update { false }
        }
    }

    fun close() {
        viewModelScope.launch {
            _events.send(CalendarEvent.NavigateBack)
        }
    }

    private suspend fun savePeriod(period: Period) {
        savePeriodUseCase.savePeriod(period)
    }

    private suspend fun deletePeriod(period: Period) {
        deletePeriodUseCase.deletePeriod(period)
    }
}
