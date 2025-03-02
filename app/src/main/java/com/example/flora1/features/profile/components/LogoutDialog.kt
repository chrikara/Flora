package com.example.flora1.features.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.uikit.dialogs.FloraDialog

@Composable
fun LogoutDialog(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {
    FloraDialog(
        modifier = modifier,
        onAccept = onAccept,
        onDismiss = onDismiss,
        title = stringResource(R.string.want_to_logout_title),
        desc = stringResource(R.string.want_to_logout_description),
        dismissOnClickOutside = true,
        dismissOnBackPress = true,
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    Flora1Theme {
        LogoutDialog(onAccept = { /*TODO*/ }) { }
    }
}
