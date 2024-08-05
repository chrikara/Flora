package com.example.flora1.core.uikit.datepickers

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BornDatePicker(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState = rememberDatePickerState()
) {
    DatePicker(
        modifier = modifier,
        title = null,
        state = datePickerState,
    )
}

