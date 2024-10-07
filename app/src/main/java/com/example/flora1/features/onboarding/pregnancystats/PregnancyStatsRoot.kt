package com.example.flora1.features.onboarding.pregnancystats

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import com.example.flora1.core.presentation.ui.uikit.buttons.MultipleOptionsButton
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.core.presentation.ui.uikit.dropdown.DropdownWithBorderWithInlineLabel
import com.example.flora1.features.onboarding.weight.NumericalOptions
import com.example.flora1.features.onboarding.weight.PregnancyStatsViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PregnancyStatsRoot(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: PregnancyStatsViewModel = hiltViewModel(),
) {
    val pregnancy by viewModel.pregnancies.collectAsStateWithLifecycle()
    val miscarriage by viewModel.miscarriages.collectAsStateWithLifecycle()
    val abortion by viewModel.abortions.collectAsStateWithLifecycle()
    val isBreastfeeding by viewModel.isBreastfeeding.collectAsStateWithLifecycle()
    val booleanChoices = listOf(true, false)

    ConstraintLayout(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.statusBars.asPaddingValues()),
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
                    painter = painterResource(id = R.drawable.flora_logo_new),
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
                text = "Tell us some things about your pregnancy background",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "This will help us better understand you and modify Flora according to your needs",
                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

        }

        Column(
            modifier = Modifier

                .constrainAs(mainContent) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(bottomBar.top)

                }
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column {
                Text(
                    text = "Are you currently breastfeeding?",
                    fontFamily = FontFamily(Font(R.font.raleway_regular)),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
                MultipleOptionsButton(
                    selectedOption = isBreastfeeding,
                    options = booleanChoices,
                    onSelectedOption = { isBreastfeeding : Boolean ->
                        viewModel.onBreastfeedingChanged(isBreastfeeding)
                    },
                    text = {
                        if(this) "Yes" else "No"
                    },
                )
            }

            DropdownWithBorderWithInlineLabel(
                selectedItem = pregnancy,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onPregnanciesChanged(it)
                },
                label = "Pregnancies"
            )

            DropdownWithBorderWithInlineLabel(
                selectedItem = miscarriage,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onMiscarriagesChanged(it)
                },
                label = "Miscarriages"
            )

            DropdownWithBorderWithInlineLabel(
                selectedItem = abortion,
                itemText = { it.text },
                items = NumericalOptions.entries.toTypedArray(),
                onItemSelected = {
                    viewModel.onAbortionsChanged(it)
                },
                label = "Abortions"
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
                viewModel.onSaveTotalPregnancies(pregnancy ?: NumericalOptions.ZERO)
                viewModel.onSaveTotalMiscarriages(miscarriage ?: NumericalOptions.ZERO)
                viewModel.onSaveTotalAbortions(abortion ?: NumericalOptions.ZERO)
                onNext()
            },
        )

        Spacer(modifier = Modifier
            .constrainAs(spacer) {
                bottom.linkTo(parent.bottom)
            }
            .height(
                WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ))
    }
}


