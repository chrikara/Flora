package com.example.flora1.core.presentation.ui.uikit.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flora1.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    onCloseButtonClicked: () -> Unit,
) {
    Icon(
        modifier = modifier
            .testTag(tag = stringResource(id = R.string.close_icon_test_tag))
            .size(size = 26.dp)
            .clickable(
                enabled = true,
                onClick = onCloseButtonClicked,
            ),
        imageVector = Icons.Default.Close,
        contentDescription = "",
        tint = iconTint,
    )
}
