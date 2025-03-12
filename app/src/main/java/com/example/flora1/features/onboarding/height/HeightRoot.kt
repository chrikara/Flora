package com.example.flora1.features.onboarding.usernameage

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.uikit.textfields.UnitTextField
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.height.HeightViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HeightRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel(),
) {
    val height by viewModel.height.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()

    OnBoardingScaffold(
        title = "What is your height?",
        isNextEnabled = enabled,
        selectedScreen = OnBoardingScreen.HEIGHT,
        onBackClick = onBack,
        onNextClick = {
            viewModel.onSaveHeight(height.text.toFloat())
            onNext()
        },
    ) {
        HeightContent(
            height = height,
            onValueChange = viewModel::onHeightChanged,
        )
    }
}

@Composable
internal fun HeightContent(
    modifier: Modifier = Modifier,
    height: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    UnitTextFieldContent(
        modifier = modifier,
        value = height,
        onValueChange = onValueChange,
        unit = stringResource(R.string.cm),
    )
}

@Composable
internal fun UnitTextFieldContent(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    unit: String,
    onValueChange: (TextFieldValue) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
        onValueChange(value.copy(selection = TextRange(value.text.length)))
    }

    UnitTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        unit = unit,
    )
}
