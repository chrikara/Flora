package com.example.flora1.features.main

import androidx.compose.runtime.Composable
import com.example.flora1.features.main.components.PeriodSphere

@Composable
fun MainRoot(
    onTextPeriodTrackClick: () -> Unit,
) {

    PeriodSphere(onTextPeriodTrackClick = onTextPeriodTrackClick)

}
