@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.flora1.R

@Composable
internal fun WeightItem(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    weight: TextFieldValue,
    isValid: (String) -> Boolean = {
        true
    },
    onButtonClicked: (TextFieldValue) -> Unit,
) {
    PersonalDetailsUnitItem(
        modifier = modifier,
        sheetState = sheetState,
        value = weight,
        onButtonClicked = onButtonClicked,
        title = stringResource(id = R.string.weight),
        unit = stringResource(id = R.string.kg),
        isValid = isValid,
    )
}
