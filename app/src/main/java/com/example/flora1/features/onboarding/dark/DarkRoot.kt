package com.example.flora1.features.onboarding.dark

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.uikit.radio.RadioGroup
import com.example.flora1.domain.Theme
import com.example.flora1.features.onboarding.OnBoardingScreen
import com.example.flora1.features.onboarding.components.OnBoardingScaffold

@Composable
fun DarkRoot(
    viewModel: DarkViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val selectedTheme by viewModel.theme.collectAsStateWithLifecycle()

    DarkRoot(
        selectedTheme = selectedTheme,
        onSelectedThemeChanged = viewModel::changeTheme,
        onNextClick = onNextClick,
        onBackClick = onBackClick,
    )

}

@Composable
fun DarkRoot(
    selectedTheme: Theme,
    onSelectedThemeChanged: (Theme) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    OnBoardingScaffold(
        title = "Select your theme",
        description = "You may choose light or dark mode for your preferred navigation of Flora!",
        selectedScreen = OnBoardingScreen.DARK,
        onBackClick = onBackClick,
        onNextClick = onNextClick,
    ) {
        RadioGroup(
            radioButtons = Theme.entries.toTypedArray(),
            selectedRadioButton = selectedTheme,
            radioButtonLabel = {
                it.text(context)
            },
            onRadioButtonSelected = onSelectedThemeChanged,
        )
    }
}

private fun Theme.text(
    context: Context,
) =
    when (this) {
        Theme.AUTO -> context.getString(R.string.auto_mode)
        Theme.LIGHT -> context.getString(R.string.light_mode)
        Theme.DARK -> context.getString(R.string.dark_mode)
    }
