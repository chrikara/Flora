package com.example.flora1.features.onboarding.usernameage

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.core.presentation.ui.uikit.textfields.UnitTextField
import com.example.flora1.features.onboarding.height.HeightViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HeightRoot(
    onNext: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel(),
) {
    val height by viewModel.height.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
        viewModel.onHeightChanged(
            height = height.copy(
                selection = TextRange(height.text.length) // Set cursor to the end
            )
        )
    }

    BackHandler {}

    ConstraintLayout(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
    ) {
        val (topBar, mainContent, bottomBar, spacer) = createRefs()


        Column(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = R.drawable.flora_logo_new),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "What is your height?",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(15.dp))

        }

        Column(
            modifier = Modifier
                .constrainAs(mainContent) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(bottomBar.top)

                }
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnitTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = height,
                onValueChange = {
                    viewModel.onHeightChanged(it)
                },
                unit = "cm"
            )
        }



        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar) {
                    bottom.linkTo(spacer.top)
                },
            text = "Next",
            enabled = enabled,
            onClick = {
                viewModel.onSaveHeight(height.text.toFloat())
                onNext()
            },
        )

        Spacer(modifier = Modifier
            .constrainAs(spacer) {
                bottom.linkTo(parent.bottom)
            }
            .height(
                WindowInsets.ime
                    .asPaddingValues()
                    .calculateBottomPadding()
            ))
    }
}
