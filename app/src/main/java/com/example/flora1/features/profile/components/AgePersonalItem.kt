@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flora1.R
import com.example.flora1.features.onboarding.born.BornRootContent
import com.example.flora1.features.onboarding.born.yearsToMillis
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
internal fun AgePersonalItem(
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    selectedDate: Long,
    onButtonClicked: suspend (Long) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val datePickerState = rememberProfileDatePickerState()
    val title = stringResource(id = R.string.age)
    val resetDate = {
        datePickerState.selectedDateMillis = selectedDate
        datePickerState.displayedMonthMillis = selectedDate
        datePickerState.displayMode = DisplayMode.Picker
    }

    LaunchedEffect(key1 = selectedDate) {
        resetDate()
    }

    OnBoardingItem(
        text = title,
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
    )

    if (sheetState.isVisible) {
        PersonalDetailsBottomSheet(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = resetDate,
            title = stringResource(id = R.string.age),
            state = sheetState,
            onButtonClicked = {
                onButtonClicked(datePickerState.selectedDateMillis!!)
            },
        ) {
            BornRootContent(
                datePickerState = datePickerState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberProfileDatePickerState(
    initialSelectedDateMillis: Long? = null,
    initialDisplayedMonthMillis: Long? = initialSelectedDateMillis,
) = rememberDatePickerState(
    initialSelectedDateMillis = initialSelectedDateMillis,
    initialDisplayedMonthMillis = initialDisplayedMonthMillis,
    selectableDates = profileSelectableDates,
)

@OptIn(ExperimentalMaterial3Api::class)
private val profileSelectableDates = object : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val minSelectableMillis = System.currentTimeMillis() - yearsToMillis(13) // 13 years ago

        return utcTimeMillis in 0..minSelectableMillis
    }

    override fun isSelectableYear(year: Int): Boolean =
        year in 1951..(LocalDate.now().year - 13)
}
