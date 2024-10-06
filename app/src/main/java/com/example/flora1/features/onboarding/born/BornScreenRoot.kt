package com.example.flora1.features.onboarding.born

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flora1.R
import com.example.flora1.core.uikit.buttons.PrimaryButton
import com.example.flora1.core.uikit.datepickers.BornDatePicker
import com.example.flora1.core.uikit.datepickers.rememberFloraDatePickerState

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BornScreenRoot(
    onNext: (isEligible: Boolean) -> Unit,
    viewModel: BornScreenViewModel = hiltViewModel(),
) {

    val datePickerState = rememberFloraDatePickerState()

    BackHandler {}

    ConstraintLayout(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.systemBars.asPaddingValues()),
    ) {
        val (column, datePicker, button, spacer) = createRefs()


        Column(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(parent.top)
            },
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
                text = "When were you born?",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Since cycles can change over time, this helps us customize the app for you.",
                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(30.dp))

        }

        BornDatePicker(
            modifier = Modifier.constrainAs(datePicker) {
                top.linkTo(column.bottom, margin = 20.dp)
                bottom.linkTo(button.top)
            },
            datePickerState = datePickerState,
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button) {
                    bottom.linkTo(spacer.top)
                },
            enabled =
            if (datePickerState.selectedDateMillis == null)
                false
            else
                datePickerState.selectedDateMillis!! < System.currentTimeMillis(),
            text = "Next",
            onClick = {
                if (isCurrentDateLessThanYears(datePickerState.selectedDateMillis!!, 13))
                    onNext(false)
                else {
                    viewModel.onSaveDateOfBirth(datePickerState.selectedDateMillis!!)
                    onNext(true)
                }
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


fun isCurrentDateLessThanYears(dateSelected: Long, years: Int) =
    System.currentTimeMillis() - dateSelected <= yearsToMillis(years = years)


fun yearsToMillis(years: Int): Long {
    val daysInYear = 365.25 // Average days in a year, accounting for leap years
    val millisInDay = 24 * 60 * 60 * 1000L // Hours * Minutes * Seconds * Milliseconds
    return (years * daysInYear * millisInDay).toLong()
}
