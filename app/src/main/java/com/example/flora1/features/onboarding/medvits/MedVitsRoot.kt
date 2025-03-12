package com.example.flora1.features.onboarding.medvits

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.gynosurgery.ButtonsWithDescriptionContent

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MedVitsRoot(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: MedVitsViewModel = hiltViewModel(),
) {
    val isTakingMedVits by viewModel.isTakingMedVits.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    OnBoardingScaffold(
        verticalArrangement = Arrangement.Top,
        selectedScreen = OnBoardingScreen.MED_VITS,
        title = "Have you ever received any medication or vitamins?",
        onBackClick = onBack,
        onNextClick = {
            viewModel.onSaveHasTakenMedVits(isTakingMedVits)
            viewModel.onSaveMedVitsDescription(
                if (isTakingMedVits)
                    description
                else
                    ""
            )
            onNext()
        },
    ) {
        MedVitsContent(
            selectedOption = isTakingMedVits,
            onSelectedOptionChanged = viewModel::onIsTakingMedVitsChanged,
            description = description,
            onDescriptionChanged = viewModel::onDescriptionChanged
        )
    }
}

@Composable
internal fun MedVitsContent(
    selectedOption: Boolean,
    onSelectedOptionChanged: (Boolean) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    ButtonsWithDescriptionContent(
        selectedOption = selectedOption,
        onSelectedOptionChanged = onSelectedOptionChanged,
        label = stringResource(R.string.medvits_label),
        placeholder = stringResource(R.string.medvits_placeholder),
        description = description,
        onDescriptionChanged = onDescriptionChanged
    )
}
