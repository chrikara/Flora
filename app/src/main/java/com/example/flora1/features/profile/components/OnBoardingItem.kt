@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import kotlinx.coroutines.launch

@Composable
fun <T : Enum<T>> OnBoardingItem(
    modifier: Modifier = Modifier,
    text: String = "Item",
    sheetState: SheetState = rememberModalBottomSheetState(),
    options: List<T>,
    optionName: (T) -> String,
    onButtonClicked: (T?) -> Unit,
    selectedOption: T?,
) {
    val scope = rememberCoroutineScope()
    OnBoardingItem(
        modifier = modifier,
        text = text,
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
    )

    SingleChoicePersonalDetailsBottomSheet(
        title = text,
        state = sheetState,
        options = options,
        optionName = optionName,
        onButtonClicked = onButtonClicked,
        selectedOption = selectedOption,
    )
}

@Composable
fun <T : Enum<T>> OnBoardingItem(
    modifier: Modifier = Modifier,
    text: String = "Item",
    sheetState: SheetState = rememberModalBottomSheetState(),
    options: List<T>,
    optionName: (T) -> String,
    onButtonClicked: (List<T>) -> Unit,
    selectedOptions: List<T>,
) {
    val scope = rememberCoroutineScope()
    OnBoardingItem(
        modifier = modifier,
        text = text,
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
    )

    MultiChoicePersonalDetailsBottomSheet(
        title = text,
        state = sheetState,
        options = options,
        optionName = optionName,
        onButtonClicked = onButtonClicked,
        selectedOptions = selectedOptions,
    )
}


@Composable
fun OnBoardingItem(
    modifier: Modifier = Modifier,
    text: String = "Item",
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_onboarding_next_arrow),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}
