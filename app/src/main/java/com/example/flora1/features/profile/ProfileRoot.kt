@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.designsystem.getPrimaryHorizontalBrush
import com.example.flora1.core.presentation.ui.observers.ObserveAsEvents
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.core.presentation.ui.uikit.buttons.CircleCloseButton
import com.example.flora1.domain.Theme
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import com.example.flora1.features.profile.components.AgePersonalItem
import com.example.flora1.features.profile.components.AverageCyclePersonalItem
import com.example.flora1.features.profile.components.GynosurgeryPersonalItem
import com.example.flora1.features.profile.components.HeightPersonalItem
import com.example.flora1.features.profile.components.MedVitsPersonalItem
import com.example.flora1.features.profile.components.OnBoardingItem
import com.example.flora1.features.profile.components.WeightPersonalItem
import com.example.flora1.features.profile.consent.ProfileEvent

@Composable
fun ProfileRoot(
    onBack: () -> Unit,
    onNavigateToManageConsent: () -> Unit,
    onNavigateToMyDoctors: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val theme by viewModel.theme.collectAsStateWithLifecycle()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val isPredictionModeEnabled by viewModel.isPredictionModeEnabled.collectAsStateWithLifecycle()
    val pregnancyStatus by viewModel.pregnancyStatus.collectAsStateWithLifecycle()
    val race by viewModel.race.collectAsStateWithLifecycle()
    val contraceptiveMethods by viewModel.contraceptiveMethods.collectAsStateWithLifecycle()
    val height by viewModel.height.collectAsStateWithLifecycle()
    val weight by viewModel.weight.collectAsStateWithLifecycle()
    val averageCycleDays by viewModel.averageCycleDays.collectAsStateWithLifecycle()
    val medVitsDescription by viewModel.medVitsDescription.collectAsStateWithLifecycle()
    val hasTakenMedvits by viewModel.hasTakenMedvits.collectAsStateWithLifecycle()
    val gynosurgeryDescription by viewModel.gynosurgeryDescription.collectAsStateWithLifecycle()
    val hasDoneGynosurgery by viewModel.hasDoneGynosurgery.collectAsStateWithLifecycle()
    val dateOfBirth by viewModel.dateOfBirth.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            ProfileEvent.NavigateBack -> onBack()
            ProfileEvent.NavigateToManageConsent -> onNavigateToManageConsent()
            ProfileEvent.NavigateToMyDoctorsSuccess -> onNavigateToMyDoctors()
            is ProfileEvent.NavigateToMyDoctorsFailed -> context.showSingleToast(
                context.getString(R.string.enable_manage_data_consent)
            )
        }
    }

    ProfileRoot(
        onAction = viewModel::onAction,
        theme = theme,
        isLoggedIn = isLoggedIn,
        isPredictionModeEnabled = isPredictionModeEnabled,
        selectedPregnancyStatus = pregnancyStatus,
        selectedRace = race,
        selectedContraceptiveMethods = contraceptiveMethods,
        selectedHeight = height,
        selectedWeight = weight,
        selectedAverageCycleDays = averageCycleDays,
        isHeightValid = viewModel.heightValidator::isHeightValid,
        isWeightValid = viewModel.weightValidator::isWeightValid,
        medVitsDescription = medVitsDescription,
        hasTakenMedVits = hasTakenMedvits,
        gynosurgeryDescription = gynosurgeryDescription,
        hasDoneGynosurgery = hasDoneGynosurgery,
        dateOfBirth = dateOfBirth,
    )
}

@Composable
fun ProfileRoot(
    theme: Theme = Theme.AUTO,
    isLoggedIn: Boolean = false,
    onAction: (ProfileAction) -> Unit = {},
    isPredictionModeEnabled: Boolean = false,
    selectedPregnancyStatus: PregnancyStatus = PregnancyStatus.PREGNANT,
    selectedRace: Race = Race.NO_COMMENT,
    selectedHeight: String = "",
    selectedWeight: String = "",
    medVitsDescription: String = "",
    hasTakenMedVits: Boolean = false,
    gynosurgeryDescription: String = "",
    hasDoneGynosurgery: Boolean = false,
    isHeightValid: (String) -> Boolean = { true },
    isWeightValid: (String) -> Boolean = { true },
    selectedContraceptiveMethods: List<ContraceptiveMethod> = emptyList(),
    selectedAverageCycleDays: Int = 1,
    dateOfBirth: Long = 10L,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CircleCloseButton(
                onClick = { onAction(ProfileAction.OnBackClicked) }
            )

            Text(
                text = stringResource(R.string.version_flora),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(30.dp))

        /*
        We don't know yet if greek language support is required.
         */
//
//        PrimaryInfoRow(
//            primaryText = stringResource(R.string.language),
//            secondaryText = "Choose your preferred language",
//            leadingIconRes = R.drawable.ic_language
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))

        PrimaryInfoRow(
            primaryText = when (theme) {
                Theme.AUTO -> stringResource(R.string.auto_mode)
                Theme.LIGHT -> stringResource(R.string.light_mode)
                Theme.DARK -> stringResource(R.string.dark_mode)
            },
            secondaryText = stringResource(R.string.tap_to_toggle),
            leadingIconRes = when (theme) {
                Theme.AUTO -> R.drawable.person
                Theme.LIGHT -> R.drawable.ic_light_mode
                Theme.DARK -> R.drawable.ic_dark_mode
            },
            onClick = {
                onAction(ProfileAction.OnChangeTheme)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))


        PrimaryInfoRowWithSwitch(
            primaryText = stringResource(R.string.enable_predictions),
            secondaryText = stringResource(R.string.enable_predictions_description),
            leadingIconRes = R.drawable.ic_didroom,
            checked = isPredictionModeEnabled,
            onClick = { onAction(ProfileAction.OnEnablePredictionModeClicked) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryInfoRow(
            primaryText = stringResource(R.string.manage_data_consent),
            leadingIconRes = null,
            trailingIconRes = R.drawable.ic_onboarding_next_arrow,
            onClick = { onAction(ProfileAction.OnManageConsentClicked) },
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryInfoRow(
            primaryText = stringResource(R.string.my_doctors),
            leadingIconRes = R.drawable.ic_doctor,
            trailingIconRes = R.drawable.ic_onboarding_next_arrow,
            onClick = { onAction(ProfileAction.OnMyDoctorsClicked) },
        )

        Spacer(modifier = Modifier.height(20.dp))

        InfoRow(enabled = false) {
            Column {
                AgePersonalItem(
                    selectedDate = dateOfBirth,
                    onButtonClicked = {
                        onAction(ProfileAction.OnDateOfBirthButtonClicked(it))
                    }
                )
                OnBoardingItem(
                    text = stringResource(R.string.pregnancy_stats),
                    options = PregnancyStatus.entries,
                    selectedOption = selectedPregnancyStatus,
                    optionName = PregnancyStatus::value,
                    onButtonClicked = {
                        onAction(ProfileAction.OnPregnancyStatButtonClicked(it))
                    },
                )
                OnBoardingItem(
                    text = stringResource(R.string.race),
                    options = Race.entries,
                    selectedOption = selectedRace,
                    optionName = Race::text,
                    onButtonClicked = {
                        onAction(ProfileAction.OnRaceButtonClicked(it))
                    },
                )

                HeightPersonalItem(
                    height = TextFieldValue(selectedHeight),
                    onButtonClicked = {
                        onAction(ProfileAction.OnHeightButtonClicked(it.text))
                    },
                    isValid = isHeightValid,
                )

                WeightPersonalItem(
                    weight = TextFieldValue(selectedWeight),
                    onButtonClicked = {
                        onAction(ProfileAction.OnWeightButtonClicked(it.text))
                    },
                    isValid = isWeightValid,
                )


                MedVitsPersonalItem(
                    description = medVitsDescription,
                    isEnabled = hasTakenMedVits,
                    onButtonClicked = { enabled, description ->
                        onAction(ProfileAction.OnChangeMedvitsClicked(enabled, description))
                    }
                )

                GynosurgeryPersonalItem(
                    description = gynosurgeryDescription,
                    isEnabled = hasDoneGynosurgery,
                    onButtonClicked = { enabled, description ->
                        onAction(ProfileAction.OnChangeGynosurgeryClicked(enabled, description))
                    }
                )
                OnBoardingItem(
                    text = stringResource(R.string.contraceptives),
                    options = ContraceptiveMethod.entries,
                    selectedOptions = selectedContraceptiveMethods,
                    optionName = ContraceptiveMethod::text,
                    onButtonClicked = {
                        onAction(ProfileAction.OnContraceptiveMethodsButtonClicked(it))
                    },
                )
                AverageCyclePersonalItem(
                    title = stringResource(R.string.average_cycle),
                    day = selectedAverageCycleDays,
                    onButtonClicked = {
                        onAction(ProfileAction.OnAverageCycleDaysButtonClicked(it))
                    }
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        PrimaryInfoButton(
            brush = getPrimaryHorizontalBrush(),
            text = stringResource(
                if (isLoggedIn)
                    R.string.login
                else
                    R.string.logout,
            ),
            onClick = {},
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))

    }
}

@Composable
fun PrimaryInfoRowWithSwitch(
    onClick: () -> Unit = {},
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant,
        )
    ),
    enabled: Boolean = true,
    primaryText: String = "Dark Mode",
    secondaryText: String? = null,
    @DrawableRes leadingIconRes: Int? = R.drawable.eye_closed,
    checked: Boolean = false,
    onCheckedChanged: ((Boolean) -> Unit)? = null,
) {
    PrimaryInfoRow(
        onClick = onClick,
        brush = brush,
        enabled = enabled,
        primaryText = primaryText,
        secondaryText = secondaryText,
        leadingIconRes = leadingIconRes,
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onCheckedChanged)
        }
    )
}


@Composable
fun PrimaryInfoRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant,
        )
    ),
    enabled: Boolean = true,
    primaryText: String = "Dark Mode",
    primaryTextAlignment: Alignment.Horizontal = Alignment.Start,
    secondaryText: String? = null,
    @DrawableRes leadingIconRes: Int? = R.drawable.eye_closed,
    @DrawableRes trailingIconRes: Int,
) {
    PrimaryInfoRow(
        modifier = modifier,
        onClick = onClick,
        brush = brush,
        leadingIconRes = leadingIconRes,
        enabled = enabled,
        primaryText = primaryText,
        primaryTextAlignment = primaryTextAlignment,
        secondaryText = secondaryText,
        trailingContent = {
            Icon(
                painter = painterResource(id = trailingIconRes),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    )
}

@Composable
fun PrimaryInfoRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant,
        )
    ),
    enabled: Boolean = true,
    primaryText: String = "Dark Mode",
    primaryTextAlignment: Alignment.Horizontal = Alignment.Start,
    secondaryText: String? = null,
    @DrawableRes leadingIconRes: Int? = R.drawable.eye_closed,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    InfoRow(
        modifier = modifier,
        onClick = onClick,
        brush = brush,
        enabled = enabled,
    ) {
        leadingIconRes?.let {
            Icon(
                painter = painterResource(id = leadingIconRes),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }


        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                modifier = Modifier.align(primaryTextAlignment),
                text = primaryText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,

                )

            Spacer(modifier = Modifier.height(5.dp))

            secondaryText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }

        trailingContent?.invoke()
    }
}

@Composable
private fun PrimaryInfoButton(
    text: String = "Login",
    onClick: () -> Unit,
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant,
        )
    ),
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    InfoRow(
        onClick = onClick,
        brush = brush,
        enabled = true,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
    }
}

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant,
        )
    ),
    enabled: Boolean,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .background(
                brush = brush
            )
            .padding(horizontal = 15.dp, vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

@Composable
@PreviewLightDark
fun Preview(modifier: Modifier = Modifier) {
    Flora1Theme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ProfileRoot(

            )
        }
    }
}
