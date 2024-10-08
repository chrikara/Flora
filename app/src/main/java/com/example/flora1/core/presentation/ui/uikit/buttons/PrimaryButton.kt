package com.example.flora1.core.presentation.ui.uikit.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.flora1.core.presentation.designsystem.DisabledAlphaText
import com.example.flora1.core.presentation.designsystem.getPrimaryHorizontalBrush

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 0.dp,
        vertical = 0.dp,
    ),
) {

    Button(
        modifier = modifier,
        text = text,
        enabled = enabled,
        onClick = onClick,
        textColor = if (enabled)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onPrimary.copy(alpha = DisabledAlphaText),
        leadingIcon = leadingIcon,
        textStyle = textStyle,
        brush = getPrimaryHorizontalBrush(isEnabled = enabled),
        paddingValues = paddingValues,
    )
}
