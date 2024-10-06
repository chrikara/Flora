package com.example.flora1.features.onboarding.contraceptives

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.uikit.bottomsheets.ChoiceComboBoxBottomSheet
import com.example.flora1.core.uikit.buttons.PrimaryButton
import com.example.flora1.core.uikit.textfields.ClickableTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ContraceptivesRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: ContraceptivesViewModel = hiltViewModel(),
) {
    val selectedContraceptiveMethods by viewModel.selectedContraceptiveMethods.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    BackHandler {}

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val size = 30.dp
                Icon(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .clickable(onClick = onBack)
                        .align(Alignment.Top),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "",
                )
                Image(
                    modifier = Modifier.size(75.dp),
                    painter = painterResource(id = R.drawable.flora_logo_new),
                    contentDescription = ""
                )

                Icon(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .clickable(onClick = onNext)
                        .align(Alignment.Top),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Are you using any contraceptive methods?",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(60.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
        ) {
            PrimaryButton(
                text = "Next",
                onClick = {
                    viewModel.onSaveContraceptiveMethods(contraceptiveMethods = selectedContraceptiveMethods)
                    onNext()
                },
            )
        }
    }
}
