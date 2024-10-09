package com.example.flora1.features.onboarding.pregnancystats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.presentation.ui.uikit.buttons.MultipleOptionsButton
import com.example.flora1.core.presentation.ui.uikit.dropdown.DropdownWithBorderWithInlineLabel
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import com.example.flora1.features.onboarding.weight.NumericalOptions
import com.example.flora1.features.onboarding.weight.PregnancyStatsViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PregnancyStatsRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: PregnancyStatsViewModel = hiltViewModel(),
) {
    val pregnancy by viewModel.pregnancies.collectAsStateWithLifecycle()
    val miscarriage by viewModel.miscarriages.collectAsStateWithLifecycle()
    val abortion by viewModel.abortions.collectAsStateWithLifecycle()
    val isBreastfeeding by viewModel.isBreastfeeding.collectAsStateWithLifecycle()
    val booleanChoices = listOf(true, false)

    OnBoardingScaffold(
        verticalArrangement = Arrangement.Center,
        selectedScreen = OnBoardingScreen.PREGNANCY_STATS,
        title = "Tell us some things about your pregnancy background",
        description = "This will help us better understand you and modify Flora according to your needs",
        onNextClick = {
            viewModel.onSaveTotalPregnancies(pregnancy ?: NumericalOptions.ZERO)
            viewModel.onSaveTotalMiscarriages(miscarriage ?: NumericalOptions.ZERO)
            viewModel.onSaveTotalAbortions(abortion ?: NumericalOptions.ZERO)
            onNext()
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            Column {
                Text(
                    text = "Are you currently breastfeeding?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
                MultipleOptionsButton(
                    selectedOption = isBreastfeeding,
                    options = booleanChoices,
                    onSelectedOption = { isBreastfeeding: Boolean ->
                        viewModel.onBreastfeedingChanged(isBreastfeeding)
                    },
                    text = {
                        if (this) "Yes" else "No"
                    },
                )
            }

            DropdownWithBorderWithInlineLabel(
                selectedItem = pregnancy,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onPregnanciesChanged(it)
                },
                label = "Pregnancies"
            )

            DropdownWithBorderWithInlineLabel(
                selectedItem = miscarriage,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onMiscarriagesChanged(it)
                },
                label = "Miscarriages"
            )

            DropdownWithBorderWithInlineLabel(
                selectedItem = abortion,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onAbortionsChanged(it)
                },
                label = "Abortions"
            )
        }
    }
}


