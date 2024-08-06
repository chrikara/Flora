package com.example.flora1.features.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flora1.features.main.components.PeriodSphere

@Composable
fun MainRoot(
    onTextPeriodTrackClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val selectedDay by viewModel.selectedDay.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()

    PeriodSphere(
        onTextPeriodTrackClick = onTextPeriodTrackClick,
        selectedDay = selectedDay,
        dateText = selectedDate ,
        onArcClicked = viewModel::onArcClicked,
    )
}
