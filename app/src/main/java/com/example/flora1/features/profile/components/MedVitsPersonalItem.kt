package com.example.flora1.features.profile.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.flora1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MedVitsPersonalItem(
    description: String,
    isEnabled: Boolean,
    onButtonClicked: (enabled: Boolean, description: String) -> Unit,
) {
    DescriptionPersonalItem(
        title = stringResource(id = R.string.medVits),
        label = stringResource(id = R.string.medvits_label),
        placeholder = stringResource(id = R.string.medvits_placeholder),
        onButtonClicked = onButtonClicked,
        description = description,
        isEnabled = isEnabled,
    )
}
