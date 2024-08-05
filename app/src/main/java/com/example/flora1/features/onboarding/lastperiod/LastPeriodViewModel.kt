package com.example.flora1.features.onboarding.lastperiod

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.date.performActionBetweenTwoDates
import com.example.flora1.core.date.toDate
import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.db.PeriodEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class LastPeriodViewModel @Inject constructor(
    private val db: PeriodDatabase,
) : ViewModel() {


    private fun onSaveLastPeriod(periodEntity: PeriodEntity, callback: (Result<Boolean>) -> Unit = {}) {
        viewModelScope.launch {
            val result = try {
                withContext(Dispatchers.IO) {
                    db.dao.savePeriodEntry(periodEntity)
                }
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }

            callback(result)
        }
    }

    fun onNextClicked(
        datePickerState: DateRangePickerState,
    ) {
        val startingDate = datePickerState.selectedStartDateMillis!!.toDate()
        val endingDate = datePickerState.selectedEndDateMillis?.toDate()

        if (endingDate != null) {
            performActionBetweenTwoDates(
                startingDate = startingDate, endingDate = endingDate,
            ) { currentDateIterated ->
                onSaveLastPeriod(
                    PeriodEntity(
                        flow = "heavy",
                        day = currentDateIterated.dayOfMonth,
                        month = currentDateIterated.monthValue,
                        year = currentDateIterated.year,
                    )
                )
            }
            return
        }

        onSaveLastPeriod(
            PeriodEntity(
                flow = "heavy",
                day = startingDate.dayOfMonth,
                month = startingDate.monthValue,
                year = startingDate.year,
            )
        )
    }
}

