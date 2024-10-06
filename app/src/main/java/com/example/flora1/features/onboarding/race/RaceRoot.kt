package com.example.flora1.features.onboarding.race

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.uikit.buttons.PrimaryButton
import com.example.flora1.core.uikit.dropdown.DropdownWithBorderWithInlineLabel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RaceRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: RaceViewModel = hiltViewModel(),
) {
    val selectedRace by viewModel.selectedRace.collectAsStateWithLifecycle()

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
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
            },
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
                    painter = painterResource(id = R.drawable.flora_logo),
                    contentDescription = ""
                )

                Icon(
                    modifier = Modifier
                        .size(size)
                        .alpha(0f),
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "What is your race?",
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
            DropdownWithBorderWithInlineLabel(
                selectedItem = selectedRace,
                itemText = { it.text },
                items = Race.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onSelectedRaceChanged(it)
                },
                defaultExpandedValue = true,
                label = "Race"
            )
        }



        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar) {
                    bottom.linkTo(spacer.top)
                },
            text = "Next",
            onClick = {
                viewModel.onSaveRace(selectedRace)
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
