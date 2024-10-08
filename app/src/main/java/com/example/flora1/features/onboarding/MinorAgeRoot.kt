package com.example.flora1.features.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flora1.features.onboarding.components.OnBoardingScaffold

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MinorAgeRoot(
    onBack: () -> Unit,
) {
    OnBoardingScaffold(
        verticalArrangement = Arrangement.Top,
        isNextEnabled = false,
        isBackEnabled = true,
        onBackClick = onBack,
        title = "Uh oh!",

        ) {
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "You must be at least 13 years old to use Flora as per European instructions. Sorry for any inconvenience but everyone should abide by the law for a happy life :)",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Justify,
        )
    }
}
