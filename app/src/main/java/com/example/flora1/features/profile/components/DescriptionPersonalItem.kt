@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.flora1.features.onboarding.gynosurgery.ButtonsWithDescriptionContent
import kotlinx.coroutines.launch

@Composable
internal fun DescriptionPersonalItem(
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    title: String,
    label: String,
    placeholder: String,
    onButtonClicked: suspend (enabled: Boolean, description: String) -> Unit,
    description: String,
    isEnabled: Boolean,
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
        var temporaryDescription by remember {
            mutableStateOf(description)
        }

        var temporaryIsEnabled by remember {
            mutableStateOf(isEnabled)
        }

        PersonalDetailsBottomSheet(
            title = title,
            onButtonClicked = {
                onButtonClicked(temporaryIsEnabled, temporaryDescription)
            },
            state = sheetState,
            content = {
                ButtonsWithDescriptionContent(
                    selectedOption = temporaryIsEnabled,
                    onSelectedOptionChanged = {
                        if (it) temporaryDescription = ""
                        temporaryIsEnabled = it
                    },
                    onDescriptionChanged = {
                        temporaryDescription = it
                    },
                    label = label,
                    placeholder = placeholder,
                    description = temporaryDescription,
                )
            },
        )
    }
}
