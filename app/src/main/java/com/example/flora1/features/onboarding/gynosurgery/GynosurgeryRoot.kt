package com.example.flora1.features.onboarding.gynosurgery

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.example.flora1.core.presentation.ui.uikit.buttons.MultipleOptionsButton
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GynosurgeryRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: GynosurgeryViewModel = hiltViewModel(),
) {
    val hasDoneGynosurgery by viewModel.hasDoneGynosurgery.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val booleanChoices = listOf(true, false)
    var hasFocusedOnce by remember { mutableStateOf(false) }
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = hasDoneGynosurgery) {
        if (hasDoneGynosurgery && !hasFocusedOnce) {
            focusRequester.requestFocus()
            hasFocusedOnce = true
        }
    }


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
                text = "Have you ever performed gynecology surgery?",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(35.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MultipleOptionsButton(
                    selectedOption = hasDoneGynosurgery,
                    options = booleanChoices,
                    onSelectedOption = { isTakingMedVits: Boolean ->
                        viewModel.onIsTakingMedVitsChanged(isTakingMedVits)
                    },
                    text = {
                        if (this) "Yes" else "No"
                    },
                )
                Spacer(modifier = Modifier.height(35.dp))

                AnimatedVisibility(visible = hasDoneGynosurgery) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = description,
                        onValueChange = { viewModel.onDescriptionChanged(it) },
                        label = {
                            Text(
                                text = "Gynecology description",
                                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Give a brief description about your gynecology surgery...",
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                            )
                        },
                        minLines = 10,
                        maxLines = 10,
                    )
                }
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
                    viewModel.onSaveHasDoneGynecosurgery(hasDoneGynosurgery)
                    viewModel.onSaveGyncosurgeryDescription(
                        if (hasDoneGynosurgery)
                            description
                        else
                            ""
                    )
                    onNext()
                },
            )
        }
    }
}
