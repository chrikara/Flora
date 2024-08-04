package com.example.flora1.core.uikit.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flora1.ui.theme.conditionalPrimaryBrush
import com.example.flora1.ui.theme.disableBrush
import com.example.flora1.ui.theme.secondaryBrush

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 0.dp,
        vertical = 0.dp,
    ),
) {
    Button(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        textColor = Color(0xFF212121),
        leadingIcon = leadingIcon,
        brush = secondaryBrush,
        paddingValues = paddingValues,
    )

}
