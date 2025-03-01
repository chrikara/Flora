@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.flora1.features.onboarding.usernameage.UnitTextFieldContent
import kotlinx.coroutines.launch

@Composable
internal fun PersonalDetailsUnitItem(
    modifier: Modifier = Modifier,
    title: String,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onButtonClicked: (TextFieldValue) -> Unit,
    value: TextFieldValue,
    isValid: (String) -> Boolean,
    unit: String,
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
        var temporaryValue by remember {
            mutableStateOf(value)
        }

        PersonalDetailsBottomSheet(
            modifier = modifier,
            title = title,
            state = sheetState,
            onButtonClicked = {
                scope.launch {
                    onButtonClicked(temporaryValue)
                    sheetState.hide()
                }
            },
        ) {

            UnitTextFieldContent(
                modifier = modifier.align(Alignment.CenterHorizontally),
                value = temporaryValue,
                onValueChange = {
                    if (isValid(it.text))
                        temporaryValue = it
                },
                unit = unit,
            )
        }
    }
}
