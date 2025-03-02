package com.example.flora1.features.onboarding.gynosurgery

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.uikit.buttons.MultipleOptionsButton
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GynosurgeryRoot(
    onNext: () -> Unit,
    viewModel: GynosurgeryViewModel = hiltViewModel(),
) {
    val hasDoneGynosurgery by viewModel.hasDoneGynosurgery.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()

    OnBoardingScaffold(
        verticalArrangement = Arrangement.Top,
        selectedScreen = OnBoardingScreen.GYNECOSURGERY,
        title = stringResource(R.string.onboarding_title_gynosurgery),
        onNextClick = {
            viewModel.onSaveHasDoneGynecosurgery(hasDoneGynosurgery)
            viewModel.onSaveGyncosurgeryDescription(
                if (hasDoneGynosurgery)
                    description
                else
                    ""
            )
            onNext()
        },
    ) {
        GynosurgeryContent(
            selectedOption = hasDoneGynosurgery,
            onSelectedOptionChanged = viewModel::onIsTakingMedVitsChanged,
            description = description,
            onDescriptionChanged = viewModel::onDescriptionChanged,
        )
    }
}

@Composable
internal fun GynosurgeryContent(
    selectedOption: Boolean,
    onSelectedOptionChanged: (Boolean) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    ButtonsWithDescriptionContent(
        selectedOption = selectedOption,
        onSelectedOptionChanged = onSelectedOptionChanged,
        label = stringResource(R.string.gynosurgery_label),
        placeholder = stringResource(R.string.gynosurgery_placeholder),
        description = description,
        onDescriptionChanged = onDescriptionChanged
    )
}

@Composable
internal fun ButtonsWithDescriptionContent(
    selectedOption: Boolean,
    onSelectedOptionChanged: (Boolean) -> Unit,
    label: String,
    placeholder: String,
    description: String,
    onDescriptionChanged: (String) -> Unit,
) {
    val booleanChoices = listOf(true, false)
    var hasFocusedOnce by remember { mutableStateOf(false) }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(key1 = selectedOption) {
        if (selectedOption && !hasFocusedOnce) {
            focusRequester.requestFocus()
            hasFocusedOnce = true
        }
    }

    MultipleOptionsButton(
        selectedOption = selectedOption,
        options = booleanChoices,
        onSelectedOption = onSelectedOptionChanged,
        text = {
            if (this) "Yes" else "No"
        },
    )
    Spacer(modifier = Modifier.height(35.dp))

    AnimatedVisibility(visible = selectedOption) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = description,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = onDescriptionChanged,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            minLines = 10,
            maxLines = 10,
        )
    }
}
