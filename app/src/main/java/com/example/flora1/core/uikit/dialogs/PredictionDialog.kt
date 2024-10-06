package com.example.flora1.core.uikit.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.flora1.R

@Composable
fun PredictionDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    desc: AnnotatedString? = null,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {
    FloraDialog(
        modifier = modifier,
        onAccept = onAccept,
        onDismiss = onDismiss,
        title = title,
        desc = desc,
        headerImage = {
            HeaderImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
            )
        }
    )

}


@Composable
private fun HeaderImage(modifier: Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.clever_woman
        )
    )
    val progress = animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = { progress.value },
        modifier = modifier
    )
}
