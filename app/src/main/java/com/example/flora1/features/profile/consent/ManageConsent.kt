package com.example.flora1.features.profile.consent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.uikit.ErrorScreen
import com.example.flora1.core.presentation.ui.uikit.LoadingScreen
import com.example.flora1.core.presentation.ui.uikit.buttons.BackButton
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.getOrNull

@Composable
fun ManageConsentContent(
    onBack: () -> Unit,
    viewModel: ManageConsentViewModel = hiltViewModel(),
) {
    val hasGivenConsentResult by viewModel.hasGivenConsentResult.collectAsStateWithLifecycle()

    ManageConsentRoot(
        onBack = onBack,
        hasGivenConsent = hasGivenConsentResult.getOrNull() == true,
        isRunning = hasGivenConsentResult is Result.Running,
        onSwitched = viewModel::onToggleDataConsent,
        onRetry = viewModel::onRetry,
        isError = hasGivenConsentResult is Result.Error
    )
}

@Composable
fun ManageConsentRoot(
    isError: Boolean = false,
    isRunning: Boolean = false,
    hasGivenConsent: Boolean = false,
    onRetry: () -> Unit,
    onBack: () -> Unit = {},
    onSwitched: () -> Unit = { },
) {
    when {
        isError -> ErrorScreen(
            onRetryButtonClicked = onRetry,
        )

        isRunning -> LoadingScreen()

        else -> ManageConsentContent(
            onBack = onBack,
            checked = hasGivenConsent,
            onSwitched = onSwitched,
        )
    }
}


@Composable
fun ManageConsentContent(
    onBack: () -> Unit = {},
    checked: Boolean = true,
    onSwitched: () -> Unit = { },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        ManageConsentRow {
            BackButton(
                onClick = onBack,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Manage Data Consent",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        ManageConsentRow {
            Text(
                modifier = Modifier

                    .weight(1f)
                    .padding(end = 15.dp),
                text = "Consent to your data being managed when doctors ask",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            Switch(
                enabled = !checked,
                checked = checked,
                onCheckedChange = {
                    onSwitched()
                },
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(horizontal = 15.dp),
            text = "Flora provides a \"Manage Data Consent\" system, empowering you to share your information securely with doctors. By enabling this feature, doctors can request your permission to access your data to provide better assistance. You remain in full control and can revoke a doctor's access to your data at any time",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ManageConsentRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
@PreviewLightDark
private fun Preview(modifier: Modifier = Modifier) {
    Flora1Theme {
        ManageConsentContent()
    }
}
