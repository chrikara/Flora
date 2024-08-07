package com.example.flora1.features.main

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.date.toFloraText
import com.example.flora1.data.db.PeriodDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.abs


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    db: PeriodDatabase,
) : ViewModel() {

    private val _selectedDay = MutableStateFlow(LocalDate.now().dayOfMonth)
    val selectedDay: StateFlow<Int> = _selectedDay



    val periodDaysForCurrentMonth = db.dao.getPeriodLogsForMonth(LocalDate.now().monthValue)
        .mapLatest { fetchedCurrentMonthPeriodLogs ->
            fetchedCurrentMonthPeriodLogs.map {
                it.day
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val fertileDays = periodDaysForCurrentMonth.mapLatest { periodDays ->
        val daysOfMonth = LocalDate.now().month.maxLength()

        when{
            periodDays.isEmpty() -> emptyList<Int>()
            daysOfMonth - periodDays.last() < 10 -> emptyList()
            else -> {
                buildList<Int> {
                    (periodDays.last() + 6..periodDays.last() + 11).forEach {candidateDay ->
                        if(candidateDay <= daysOfMonth){
                            add(candidateDay)
                        }else{
                            return@forEach
                        }
                    }
                }
            }
        }
    }  .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val ovulationDay: StateFlow<Int?> = fertileDays.mapLatest { fertileDays ->
        when{
            fertileDays.isEmpty() -> null
            fertileDays.size < 6 -> null
            else -> fertileDays[4]
        }
    }  .flowOn(Dispatchers.Default)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )


    val primaryText = combine(
        selectedDay, periodDaysForCurrentMonth, ovulationDay, fertileDays,
    ) { selectedDay, periodDaysForCurrentMonth, ovulationDay, fertileDays ->

        if (selectedDay == ovulationDay)
            "Ovulation Day"
        else if(selectedDay in fertileDays)
            "Fertile days"
        else if (selectedDay in periodDaysForCurrentMonth) {
            if (periodDaysForCurrentMonth.last() - selectedDay == 1)
                "1 Day Left"
            else if (periodDaysForCurrentMonth.last() == selectedDay)
                "Last Day"
            else
                "${periodDaysForCurrentMonth.last() - selectedDay} days left"
        } else if (periodDaysForCurrentMonth.isNotEmpty() && periodDaysForCurrentMonth[0] - selectedDay == 1)
            "Your next period is on 1 day"
        else if (periodDaysForCurrentMonth.isNotEmpty() && periodDaysForCurrentMonth[0] > selectedDay)
            "Your next period\nstarts on ${periodDaysForCurrentMonth[0] - selectedDay} days"
        else
            "Nothing else\nfor this month"

    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )


    val selectedDate = selectedDay.mapLatest { day ->
        val localDate = LocalDate.now()
        LocalDate.of(
            localDate.year,
            localDate.monthValue,
            day,
        ).toFloraText()
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    fun onArcClicked(
        offsetClicked: Offset,
        circlePositions: List<Pair<Float, Float>>,
        circleRadius: Float,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                circlePositions.forEachIndexed { index, position ->
                    if (abs(offsetClicked.x - position.first) < circleRadius * 2f
                        && abs(offsetClicked.y - position.second) < circleRadius * 2f
                    ) {
                        _selectedDay.update { index + 1 }
                        return@forEachIndexed
                    }
                }
            }
        }
    }
}


