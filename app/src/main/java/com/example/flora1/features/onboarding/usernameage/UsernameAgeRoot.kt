package com.example.flora1.features.onboarding.usernameage

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.usernameage.UsernameAgeViewModel.Companion.MAX_USERNAME_CHARS

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
        username = username,
        onUsernameChanged = {
            viewModel.onUsernameChange(
                username = it,
                onShowMessage = { textId ->
                    context.showSingleToast(context.getString(textId, MAX_USERNAME_CHARS))
                }
            )
        },
    )
}

@Composable
fun UsernameAgeRoot(
    onNext: () -> Unit = {},
    isNextEnabled: Boolean,
    username: String,
    onUsernameChanged: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    OnBoardingScaffold(
        title = stringResource(R.string.username_screen_title),
        onNextClick = onNext,
        isNextEnabled = isNextEnabled,
        selectedScreen = OnBoardingScreen.USERNAME_AGE,
    ) {
        UsernameRootContent(
            modifier = Modifier.focusRequester(focusRequester),
            username = username,
            onUsernameChanged = onUsernameChanged,
        )
    }
}

@Composable
private fun UsernameRootContent(
    modifier: Modifier,
    username: String,
    onUsernameChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = username,
        singleLine = true,
        onValueChange = onUsernameChanged,
        label = {
            Text(text = "Username")
        }
    )
}

@PreviewLightDark
@Composable
private fun Preview1() {

    var username by remember { mutableStateOf("") }

    Flora1Theme() {
        UsernameAgeRoot(
            isNextEnabled = true,
            username = username,
            onUsernameChanged = { username = it }
        )
    }
}

