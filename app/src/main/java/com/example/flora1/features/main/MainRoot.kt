package com.example.flora1.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.uikit.dialogs.PredictionDialog
import com.example.flora1.features.main.components.PeriodSphere
import com.example.flora1.ui.theme.PrimaryHorizontalBrush

@Composable
fun MainRoot(
    onTextPeriodTrackClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val selectedDay by viewModel.selectedDay.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val periodDays by viewModel.periodDaysForCurrentMonth.collectAsStateWithLifecycle()
    val primaryText by viewModel.primaryText.collectAsStateWithLifecycle()
    val ovulationDay by viewModel.ovulationDay.collectAsStateWithLifecycle()
    val fertileDays by viewModel.fertileDays.collectAsStateWithLifecycle()
    val shouldShowPredictionDialog by viewModel.shouldShowPredictionDialog.collectAsStateWithLifecycle()
    val shouldShowPredictions by viewModel.shouldShowPredictions.collectAsStateWithLifecycle()

    CustomBackgroundSurface()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val size = 55.dp
            Icon(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .clickable(onClick = onSettingsClick)
                    .padding(10.dp),
                imageVector = Icons.Filled.Settings,
                contentDescription = "",
            )

            Icon(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .clickable(onClick = onCalendarClick)
                    .padding(15.dp),
                imageVector = Icons.Filled.DateRange,
                contentDescription = "",
            )
        }

        PeriodSphere(
            onTextPeriodTrackClick = onTextPeriodTrackClick,
            selectedDay = selectedDay,
            dateText = selectedDate,
            ovulationDay = ovulationDay,
            shouldShowPredictions = shouldShowPredictions,
            primaryText = primaryText,
            periodDays = periodDays,
            fertileDays = fertileDays,
            onArcClicked = viewModel::onArcClicked,
        )
    }

    if (shouldShowPredictionDialog) {
        PredictionDialog(
            onAccept = {
                viewModel.onShouldShowPredictionsChanged(shouldShow = true)
                viewModel.onShouldShowPredictionDialogChanged(shouldShow = false)
            },
            onDismiss = {
                viewModel.onShouldShowPredictionDialogChanged(shouldShow = false)
            }
        )
    }


}

@Composable
private fun CustomBackgroundSurface() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.6f)
            .background(PrimaryHorizontalBrush)
    )
}
