package com.example.flora1.features

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.flora1.R
import com.example.flora1.core.uikit.buttons.PrimaryButton
import com.example.flora1.ui.theme.conditionalPrimaryBrush

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AverageCycleRoot(
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    var selectedNumber: Int? by remember { mutableStateOf(null) }



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .imePadding()
    ) {
        val (topBar, numberFlowRow, bottomBar) = createRefs()
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

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "How long is your average cycle?",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "A little hint - cycles usually last 24-35 days.",
                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(numberFlowRow) {
                    top.linkTo(topBar.bottom, margin = 15.dp)
                    bottom.linkTo(bottomBar.top, margin = 5.dp)
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState()),

            ) {
            NumbersFlowRow(
                enabled = {
                    selectedNumber == it
                },
                onClick = {
                    selectedNumber =
                        if (selectedNumber == it)
                            null
                        else
                            it
                }
            )
        }


        Row(
            modifier = Modifier.constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .clickable(onClick = onNext)
                    .weight(1f),
                color = MaterialTheme.colorScheme.primary,
                text = "No idea",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.width(25.dp))

            PrimaryButton(
                modifier = Modifier.weight(1f),
                enabled = selectedNumber != null,
                text = "Next",
                onClick = {
                    // TODO: Save to prefs
                    onNext()
                }
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NumbersFlowRow(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    enabled: (Int) -> Boolean,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        (MIN_CYCLE_DAYS..MAX_CYCLE_DAYS).forEach {
            PickNumber(
                enabled = enabled(it),
                num = it,
                onClick = { numClicked ->
                    onClick(numClicked)
                },
            )
        }
    }
}

@Composable
private fun PickNumber(
    size: Dp = 55.dp,
    num: Int,
    enabled: Boolean,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(brush = conditionalPrimaryBrush(enabled = enabled))
            .clickable(onClick = {
                onClick(num)
            }),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = num.toString(),
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.raleway_bold)),
        )

    }
}

private const val MIN_CYCLE_DAYS = 15
private const val MAX_CYCLE_DAYS = 40
