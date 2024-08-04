package com.example.flora1.core.uikit.buttons


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flora1.core.uikit.buttons.ButtonMode.Active
import com.example.flora1.core.uikit.buttons.ButtonMode.InProgress
import androidx.compose.material3.Button as Material3Button

@Composable
internal fun Button(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        text = text,
        onClick = onClick,
        mode = enabled.toButtonMode(),
        border = border,
        colors = colors,
    )
}

@Composable
internal fun Button(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    mode: ButtonMode = Active,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        mode = mode,
        border = border,
        colors = colors,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
internal fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    mode: ButtonMode = Active,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = mode.enabled,
        border = border,
        colors = colors,
    ) {
        if (mode == InProgress) {
            ProgressIndicator(
                color = colors.disabledContentColor,
            )
            Spacer(
                modifier = Modifier
                    .width(3.dp),
            )
        }

        content()
    }
}

@Composable
internal fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    val material3Colors = colors.toMaterial3()

    Material3Button(
        modifier = Modifier
            .then(modifier),
        shape = RoundedCornerShape(size = 4.dp),
        border = border,
        colors = material3Colors,
        enabled = enabled,
        onClick = onClick,
        content = content,
    )
}

@Composable
private fun ProgressIndicator(
    color: Color = MaterialTheme.colorScheme.onPrimary,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .width(18.dp)
            .height(18.dp),
        color = color,
        strokeWidth = 3.dp,
    )
}

@Composable
internal fun ButtonColors.toMaterial3(): ButtonColors =
    ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

