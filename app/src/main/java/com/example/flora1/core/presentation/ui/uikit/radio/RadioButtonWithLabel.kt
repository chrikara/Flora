package com.example.flora1.core.presentation.ui.uikit.radio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.ui.theme.Flora1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButtonWithLabel(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    enforceMinTouchTarget: Boolean = false,
) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides enforceMinTouchTarget) {
        DefaultRadioButtonWithLabel(
            modifier = modifier,
            selected = selected,
            onClick = onClick,
            label = label,
            labelStyle = labelStyle,
        )
    }
}

@Composable
private fun DefaultRadioButtonWithLabel(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .clickable {
                onClick()
                focusManager.clearFocus()
            }
            .fillMaxWidth()
            .then(modifier)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.tertiary,
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp),
            text = label,
            fontFamily = FontFamily(Font(R.font.raleway_regular)),
            style = labelStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonWithLabelPreview() {
    Flora1Theme {
        Surface(
            modifier = Modifier.padding(16.dp)
        ) {
            var selected by remember { mutableStateOf(true) }
            RadioButtonWithLabel(
                selected = selected,
                onClick = { selected = !selected },
                label = "Default",
            )
        }
    }
}
