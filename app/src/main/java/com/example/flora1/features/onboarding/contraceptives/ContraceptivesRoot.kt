package com.example.flora1.features.onboarding.contraceptives

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.presentation.ui.uikit.bottomsheets.ChoiceComboBoxBottomSheet
import com.example.flora1.core.presentation.ui.uikit.textfields.ClickableTextField
import com.example.flora1.features.onboarding.components.OnBoardingScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ContraceptivesRoot(
    onNext: () -> Unit,
    viewModel: ContraceptivesViewModel = hiltViewModel(),
) {
    val selectedContraceptiveMethods by viewModel.selectedContraceptiveMethods.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    OnBoardingScaffold(
        verticalArrangement = Arrangement.Top,
        title = "Are you using any contraceptive methods?",
        onNextClick = {
            viewModel.onSaveContraceptiveMethods(contraceptiveMethods = selectedContraceptiveMethods)
            onNext()
        },
    ) {

        ClickableTextField(
            text = when (selectedContraceptiveMethods.count()) {
                0 -> "0 methods selected (Click to Add)"
                1 -> "1 method selected"
                else -> "${selectedContraceptiveMethods.count()} methods selected"
            },
            onClick = {
                scope.launch { sheetState.show() }
            },
            trailingIcon = {
                Icon(
                    imageVector = if (sheetState.isVisible)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }
        )

        if (sheetState.isVisible)
            ChoiceComboBoxBottomSheet(
                modifier = Modifier.fillMaxHeight(0.6f),
                title = "Contraceptive Methods",
                options = ContraceptiveMethod.entries,
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                optionText = ContraceptiveMethod::text,
                isChecked = {
                    it in selectedContraceptiveMethods
                },
                onOptionClicked = {
                    if (it in selectedContraceptiveMethods)
                        viewModel.onSelectedContraceptiveMethodsChanged(
                            contraceptiveMethods = selectedContraceptiveMethods - it,
                        )
                    else
                        viewModel.onSelectedContraceptiveMethodsChanged(
                            contraceptiveMethods = selectedContraceptiveMethods + it,
                        )
                }
            )
    }
}
