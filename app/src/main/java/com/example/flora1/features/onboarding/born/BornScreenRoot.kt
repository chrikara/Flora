package com.example.flora1.features.onboarding.born

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flora1.core.presentation.ui.uikit.datepickers.BornDatePicker
import com.example.flora1.core.presentation.ui.uikit.datepickers.rememberFloraDatePickerState
import com.example.flora1.features.onboarding.components.OnBoardingScaffold

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BornScreenRoot(
    onNext: (isEligible: Boolean) -> Unit,
    viewModel: BornScreenViewModel = hiltViewModel(),
) {
    val datePickerState = rememberFloraDatePickerState()

    OnBoardingScaffold(
        title = "When were you born?",
        description = "Since cycles can change over time, this helps us customize the app for you.",
        onNextClick = {
            if (isCurrentDateLessThanYears(datePickerState.selectedDateMillis!!, 13))
                onNext(false)
            else {
                viewModel.onSaveDateOfBirth(datePickerState.selectedDateMillis!!)
                onNext(true)
            }
        },
        isNextEnabled =
        if (datePickerState.selectedDateMillis == null)
            false
        else
            datePickerState.selectedDateMillis!! < System.currentTimeMillis(),
    ) {
        BornDatePicker(datePickerState = datePickerState)
    }
}


fun isCurrentDateLessThanYears(dateSelected: Long, years: Int) =
    System.currentTimeMillis() - dateSelected <= yearsToMillis(years = years)


fun yearsToMillis(years: Int): Long {
    val daysInYear = 365.25 // Average days in a year, accounting for leap years
    val millisInDay = 24 * 60 * 60 * 1000L // Hours * Minutes * Seconds * Milliseconds
    return (years * daysInYear * millisInDay).toLong()
}
