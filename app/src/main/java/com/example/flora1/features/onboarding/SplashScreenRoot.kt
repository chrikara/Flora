package com.example.flora1.features.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.designsystem.getPrimaryLinearBrush

@Composable
fun SplashScreenRoot(
    onFinishedAnimation: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val fraction1 = remember {
        Animatable(0f)
    }

    val fraction2 = remember {
        Animatable(0f)
    }

    val catchyLine = remember {
        DEFAULT_FLORA_CATCHY_LINES.random()
    }

    BackHandler {}

    LaunchedEffect(key1 = Unit) {
        fraction1.animateTo(1f, tween(3000))
        fraction2.animateTo(1f, tween(500))
        onFinishedAnimation()
    }

    var size by remember {
        mutableFloatStateOf(0f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                size = it.width.toFloat()
            }
            .background(brush = getPrimaryLinearBrush())
            .padding(horizontal = 20.dp)
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            text = "This version of FLORA app is dedicated solely to testing its user interface." +
                    " No data will be stored or shared outside your device, and no machine learning" +
                    " features are enabled.",
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(DEFAULT_FLORA_LOGO_SIZE)
                    .graphicsLayer {
                        scaleX = fraction1.value
                        scaleY = fraction1.value
                        rotationZ = fraction1.value * 360
                        translationX = screenWidth.toPx() * fraction2.value
                        this.cameraDistance = 10f
                    }
                ,
                painter = painterResource(id = R.drawable.flora_logo_new),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = fraction1.value
                        translationX = - screenWidth.toPx() * fraction2.value
                    },
                text = catchyLine,
                fontFamily = FontFamily(Font(R.font.raleway_regular)),
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }

}

private val DEFAULT_FLORA_LOGO_SIZE = 250.dp

private val DEFAULT_FLORA_CATCHY_LINES = listOf(
    "Flora: Your #1 Private Period Tracker with Cutting-Edge Predictions",
    "Stay Private, Stay Accurate with Flora's Federated Learning",
    "Flora: Predicting Periods Privately",
    "Your Cycle, Your Privacy: Trust Flora",
    "Flora: Empowering Women with Secure, Smart Predictions",
    "Flora: The Period Tracker That Values Your Privacy",
    "Flora: Advanced Predictions, Absolute Privacy",
    "Predict and Protect: Flora's Promise of Privacy",
    "Your Privacy, Our Priority: Flora Period Tracker",
    "Flora: Where Privacy Meets Precision in Period Tracking"
)

@Preview(
    showBackground = true,
)
@Composable
private fun Preview1() {
    Flora1Theme {
        SplashScreenRoot {}
    }
}
