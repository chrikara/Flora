@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.example.flora1.features.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.designsystem.getPrimaryHorizontalBrush
import com.example.flora1.core.presentation.ui.components.FloraButtonProgressIndicator
import com.example.flora1.core.presentation.ui.uikit.buttons.Button
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import kotlinx.coroutines.launch

@Composable
fun <T : Enum<T>> SingleChoicePersonalDetailsBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    state: SheetState = rememberModalBottomSheetState(),
    options: List<T>,
    optionName: (T) -> String,
    onButtonClicked: suspend (T?) -> Unit,
    selectedOption: T?,
) {
    if (state.isVisible) {
        var temporarySelectedOption by remember {
            mutableStateOf(selectedOption)
        }
        val selected: (T?) -> Boolean = {
            it == temporarySelectedOption
        }

        PersonalDetailsBottomSheet(
            modifier = modifier,
            title = title,
            state = state,
            options = options,
            optionName = optionName,
            onButtonClicked = {
                onButtonClicked(temporarySelectedOption)
            },
            onOptionClicked = {
                temporarySelectedOption = if (selected(it)) null else it
            },
            onOptionIsSelected = selected
        )
    }
}

@Composable
fun <T : Enum<T>> MultiChoicePersonalDetailsBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    state: SheetState = rememberModalBottomSheetState(),
    options: List<T>,
    optionName: (T) -> String,
    onButtonClicked: suspend (List<T>) -> Unit,
    selectedOptions: List<T>,
) {
    if (state.isVisible) {
        var temporarySelectedOptions by remember {
            mutableStateOf(selectedOptions)
        }
        val selected: (T) -> Boolean = {
            it in temporarySelectedOptions
        }
        PersonalDetailsBottomSheet(
            modifier = modifier,
            title = title,
            state = state,
            options = options,
            optionName = optionName,
            onButtonClicked = {
                onButtonClicked(temporarySelectedOptions)
            },
            onOptionClicked = {
                temporarySelectedOptions = if (selected(it))
                    temporarySelectedOptions - it
                else
                    temporarySelectedOptions + it
            },
            onOptionIsSelected = selected,
        )
    }
}

@Composable
private fun <T : Enum<T>> PersonalDetailsBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    state: SheetState = rememberModalBottomSheetState(),
    options: List<T>,
    optionName: (T) -> String,
    onButtonClicked: suspend () -> Unit,
    onOptionClicked: (T) -> Unit,
    onOptionIsSelected: (T) -> Boolean,
) {
    PersonalDetailsBottomSheet(
        modifier = modifier,
        state = state,
        title = title,
        onButtonClicked = onButtonClicked,
    ) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            options.fastForEach { option ->
                val selected = onOptionIsSelected(option)
                InputChip(
                    selected = selected,
                    label = {
                        Text(
                            text = optionName(option),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected)
                                MaterialTheme.colorScheme.onBackground
                            else
                                MaterialTheme.colorScheme.tertiary
                        )
                    },
                    onClick = {
                        onOptionClicked(option)
                    },
                    colors = InputChipDefaults.inputChipColors(
                        containerColor = Color.Transparent,
                        selectedContainerColor = Color.Transparent,
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.tertiary
                    ),
                    shape = CircleShape
                )
            }
        }
    }
}

@Composable
internal fun PersonalDetailsBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    contentModifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    onButtonClicked: suspend () -> Unit,
    state: SheetState = rememberModalBottomSheetState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()

    var isLoading by remember {
        mutableStateOf(false)
    }
    println("mpike isLoading $isLoading enabled $enabled")
    println("mpike isEnabled $enabled || !isLoading")

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            scope.launch {
                onDismissRequest()
                state.hide()

            }
        },
        sheetState = state,
        shape = RoundedCornerShape(
            topEnd = 8.dp,
            topStart = 8.dp
        ),
        tonalElevation = 12.dp,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(
            alpha = 0.7f,
        ),
        windowInsets = WindowInsets.statusBars,
    ) {
        Column(
            modifier = contentModifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(24.dp))

            content()

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                text = stringResource(R.string.done),
                onClick = {
                    scope.launch {
                        isLoading = true
                        onButtonClicked()
                        state.hide()
                    }
                },
                textColor = MaterialTheme.colorScheme.onPrimary,
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                enabled = enabled && !isLoading,
                leadingIcon = if (!isLoading) null else {
                    {
                        FloraButtonProgressIndicator()
                    }
                },
                brush = getPrimaryHorizontalBrush(isEnabled = enabled && !isLoading),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@PreviewLightDark
@Composable
private fun MultiChoicePersonalDetailsBottomSheetPreview() {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState()
    val selectedOptions =
        listOf(ContraceptiveMethod.IUD)

    Flora1Theme {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.Button(onClick = {
                scope.launch {
                    state.show()
                }
            }) {
                Text(text = "Show bottom sheet")
            }
            if (state.isVisible)
                MultiChoicePersonalDetailsBottomSheet(
                    title = "Contraceptives",
                    state = state,
                    options = ContraceptiveMethod.entries,
                    selectedOptions = selectedOptions,
                    onButtonClicked = {},
                    optionName = ContraceptiveMethod::text,
                )
        }
    }
}

@PreviewLightDark
@Composable
private fun SingleChoicePersonalDetailsBottomSheetPreview() {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState()
    val selectedOption = ContraceptiveMethod.IUD
    Flora1Theme {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.Button(onClick = {
                scope.launch {
                    state.show()
                }
            }) {
                Text(text = "Show bottom sheet")
            }
            if (state.isVisible)
                SingleChoicePersonalDetailsBottomSheet(
                    title = "Contraceptives",
                    state = state,
                    options = ContraceptiveMethod.entries,
                    selectedOption = selectedOption,
                    onButtonClicked = {},
                    optionName = ContraceptiveMethod::text,
                )
        }
    }
}
