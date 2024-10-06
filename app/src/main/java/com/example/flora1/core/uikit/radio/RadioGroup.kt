package com.example.flora1.core.uikit.radio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flora1.ui.theme.Flora1Theme

@Composable
fun <T : Enum<T>> RadioGroup(
    modifier: Modifier = Modifier,
    radioButtons: Array<T>,
    selectedRadioButton: T? = null,
    radioButtonLabel: @Composable (T) -> String = { it.name },
    onRadioButtonSelected: (T) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        for (radioButton in radioButtons) {
            RadioButtonWithLabel(
                modifier = Modifier.padding(vertical = 15.dp),
                selected = radioButton == selectedRadioButton,
                onClick = {
                    onRadioButtonSelected(radioButton)
                },
                label = radioButtonLabel(radioButton),
                labelStyle = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private enum class RadioButton {
    FIRST,
    SECOND,
    THIRD,
}

@Preview(showBackground = true)
@Composable
fun RadioGroupPreview() {
    Flora1Theme {
        Surface(
            modifier = Modifier.padding(16.dp)
        ) {
            var selectedRadioButton by remember {
                mutableStateOf(RadioButton.FIRST)
            }
            RadioGroup(
                radioButtons = RadioButton.values(),
                selectedRadioButton = selectedRadioButton,
                onRadioButtonSelected = {selectedRadioButton = it},
            )
        }
    }
}

