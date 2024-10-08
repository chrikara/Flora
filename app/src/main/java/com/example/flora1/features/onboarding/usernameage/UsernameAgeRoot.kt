package com.example.flora1.features.onboarding.usernameage

import android.annotation.SuppressLint
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.usernameage.UsernameAgeViewModel.Companion.MAX_USERNAME_CHARS

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UsernameAgeRoot(
    onNext: () -> Unit,
    viewModel: UsernameAgeViewModel = hiltViewModel(),
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    OnBoardingScaffold(
        onNextClick = onNext,
        isNextEnabled = enabled,
        title = "What is your username?",
        isImePaddingEnabled = true,
    ) {
        OutlinedTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = username,
            singleLine = true,
            onValueChange = { username ->
                viewModel.onUsernameChange(username, onShowMessage = {
                    context.showSingleToast("Cannot have more than $MAX_USERNAME_CHARS chars.")
                })
            },
            label = {
                Text(text = "Username")
            }
        )
    }
}
