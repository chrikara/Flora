@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import kotlinx.coroutines.launch

@Composable
internal fun HeightPersonalItem(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    height: TextFieldValue,
    isValid: (String) -> Boolean = {
        true
    },
    onButtonClicked: (TextFieldValue) -> Unit,
) {
    PersonalDetailsUnitItem(
        modifier = modifier,
        sheetState = sheetState,
        value = height,
        onButtonClicked = onButtonClicked,
        title = stringResource(id = R.string.height),
        unit = stringResource(id = R.string.cm),
        isValid = isValid,
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    val state = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Flora1Theme {
        Column {
            Text(text = "click", modifier = Modifier
                .clickable { scope.launch { state.show() } })

            HeightPersonalItem(
                height = TextFieldValue("150"),
                sheetState = state,
                onButtonClicked = {}
            )
        }
    }

}
