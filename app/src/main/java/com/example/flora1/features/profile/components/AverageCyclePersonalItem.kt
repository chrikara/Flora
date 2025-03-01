package com.example.flora1.features.profile.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.flora1.features.onboarding.averagecycle.AverageCycleDaysFlowRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AverageCyclePersonalItem(
    modifier: Modifier = Modifier,
    title: String,
    day: Int,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    onButtonClicked: suspend (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    OnBoardingItem(
        text = title,
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
    )

    if (sheetState.isVisible) {
        var temporaryDaySelected by remember {
            mutableIntStateOf(day)
        }
        PersonalDetailsBottomSheet(
            modifier = modifier,
            contentModifier = Modifier.verticalScroll(rememberScrollState()),
            title = title,
            state = sheetState,
            onButtonClicked = {
                onButtonClicked(temporaryDaySelected)
            },
        ) {
            AverageCycleDaysFlowRow(
                onClick = {
                    temporaryDaySelected = it
                },
                selected = {
                    it == temporaryDaySelected
                }
            )
        }
    }
}
