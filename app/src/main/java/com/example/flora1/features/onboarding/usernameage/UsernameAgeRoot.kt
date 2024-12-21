package com.example.flora1.features.onboarding.usernameage

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.components.LoginRoot
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold

@Composable
fun UsernameAgeRoot(
    onNext: () -> Unit,
    viewModel: UsernameAgeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val username by viewModel.username.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()

    UsernameAgeRoot(
        onNext = {
            viewModel.onSaveUsername(username)
            onNext()
        },
        isNextEnabled = enabled,
    )
}

@Composable
fun UsernameAgeRoot(
    onNext: () -> Unit = {},
    isNextEnabled: Boolean,
) {
    OnBoardingScaffold(
        title = stringResource(R.string.welcome_and_login_or_register_screen_title),
        onNextClick = onNext,
        isNextEnabled = isNextEnabled,
        shouldShowNext = false,
        selectedScreen = OnBoardingScreen.USERNAME_AGE,
    ) {
        LoginRoot(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            onRegisterClicked = { _, _, _ -> },
            onLoginClicked = { _, _ -> },
            onContinueAsAnonymous = onNext,
        )
    }
}


@PreviewLightDark
@Composable
private fun Preview1() {

    var username by remember { mutableStateOf("") }

    Flora1Theme() {
        UsernameAgeRoot(
            isNextEnabled = true,
        )
    }
}

