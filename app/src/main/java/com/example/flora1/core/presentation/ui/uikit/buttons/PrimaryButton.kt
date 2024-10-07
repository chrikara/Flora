package com.example.flora1.core.presentation.ui.uikit.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.ui.theme.PrimaryHorizontalBrush
import com.example.flora1.ui.theme.PurpleGrey40
import com.example.flora1.ui.theme.PurpleGrey80
import com.example.flora1.ui.theme.conditionalPrimaryBrush

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
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
        enabled = enabled,
        onClick = onClick,
        textColor = MaterialTheme.colorScheme.onPrimary,
        leadingIcon = leadingIcon,
        brush = conditionalPrimaryBrush(enabled = enabled),
        paddingValues = paddingValues,
    )
}
