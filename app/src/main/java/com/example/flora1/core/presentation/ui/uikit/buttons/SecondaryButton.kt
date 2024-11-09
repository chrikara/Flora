package com.example.flora1.core.presentation.ui.uikit.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.flora1.core.presentation.designsystem.getPrimaryHorizontalBrush

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    shouldFillMaxWidth : Boolean = true,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 0.dp,
        vertical = 0.dp,
    ),
) {
    Button(
        modifier = modifier,
        shouldFillMaxWidth = shouldFillMaxWidth,
        text = text,
        textStyle = textStyle,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        textColor = MaterialTheme.colorScheme.onPrimary,
        leadingIcon = leadingIcon,
        brush = getPrimaryHorizontalBrush(),
        paddingValues = paddingValues,
    )

}
