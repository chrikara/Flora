package com.example.flora1.features.onboarding.weight

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.usernameage.UnitTextFieldContent

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WeightRoot(
    onNext: () -> Unit,
    viewModel: WeightViewModel = hiltViewModel(),
) {
    val weight by viewModel.weight.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()

    OnBoardingScaffold(
        title = "What is your weight?",
        isNextEnabled = enabled,
        selectedScreen = OnBoardingScreen.WEIGHT,
        onNextClick = {
            viewModel.onSaveWeight(weight.text.toFloat())
            onNext()
        },
    ) {
        WeightContent(
            weight = weight,
            onValueChange = viewModel::onWeightChanged,
        )
    }
}

@Composable
internal fun WeightContent(
    modifier: Modifier = Modifier,
    weight: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    UnitTextFieldContent(
        modifier = modifier,
        value = weight,
        onValueChange = onValueChange,
        unit = stringResource(R.string.kg),
    )
}
