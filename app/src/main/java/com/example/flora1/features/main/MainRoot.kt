package com.example.flora1.features.main

import android.graphics.Path
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flora1.R
import com.example.flora1.ui.theme.PrimaryHorizontalBrush
import kotlin.math.PI

@Composable
fun MainRoot(
    onTextPeriodTrackClick : () -> Unit,
) {

    val context = LocalContext.current
    val density = LocalDensity.current

    /*
    I can find a better solution for getting the screen's width
     */
    val displayMetrics = context.resources.displayMetrics
    val screenWidthPx = displayMetrics.widthPixels
    val screenWidth = with(density) {
        (screenWidthPx).toDp()
    }
    val innerCircleWidth = with(density) {
        (screenWidthPx.toFloat() / (1.7f)).toDp()
    }
    val primaryColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f)
            .background(PrimaryHorizontalBrush)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth()
                .height(screenWidth),
            contentAlignment = Alignment.Center,
        ) {

            Canvas(
                modifier =
                Modifier
                    .fillMaxSize()
            ) {
                val radius = size.width / 2

                drawCircle(
                    color = primaryColor,
                    center = this.center,
                    radius = radius,
                )
                val offsetFactor = 0.2f // Adjust this factor to change the offset

                val offset = radius * offsetFactor
                val totalSweep = 360f
                val startAngle = 0f

                drawArc(
                    color = White,
                    startAngle = startAngle,
                    sweepAngle = totalSweep,
                    useCenter = false,
                    topLeft = this.center.copy(
                        x = this.center.x - radius + offset,
                        y = this.center.y - radius + offset
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = radius * 2 - offset * 2,
                        height = radius * 2 - offset * 2
                    ),
                    style = Stroke(
                        width = radius * 0.1f,
                        cap = StrokeCap.Round
                    )
                )

                drawArc(
                    color = Color(0xFF1C7DE6),
                    startAngle = startAngle,
                    sweepAngle = 130f,
                    useCenter = false,
                    topLeft = this.center.copy(
                        x = this.center.x - radius + offset,
                        y = this.center.y - radius + offset
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = radius * 2 - offset * 2,
                        height = radius * 2 - offset * 2
                    ),
                    style = Stroke(
                        width = radius * 0.1f,
                        cap = StrokeCap.Round
                    )
                )

                drawArc(
                    color = Color(0xFFA01C1C),
                    startAngle = startAngle + 200f,
                    sweepAngle = 50f,
                    useCenter = false,
                    topLeft = this.center.copy(
                        x = this.center.x - radius + offset,
                        y = this.center.y - radius + offset
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        width = radius * 2 - offset * 2,
                        height = radius * 2 - offset * 2
                    ),
                    style = Stroke(
                        width = radius * 0.1f,
                        cap = StrokeCap.Round
                    )
                )

                drawCircle(
                    color = White,
                    radius = innerCircleWidth.toPx() / 2f,
                )
            }

            Box(
                modifier = Modifier
                    .width(innerCircleWidth)
                    .height(innerCircleWidth)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Thu, 12 May",
                        fontFamily = FontFamily(Font(R.font.raleway_regular)),
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Period In",
                        fontFamily = FontFamily(Font(R.font.raleway_bold)),
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "1 Day",
                        fontFamily = FontFamily(Font(R.font.raleway_extrabold)),
                        fontSize = 36.sp,
                        color = primaryColor,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.clickable(
                            indication = null,
                            onClick = onTextPeriodTrackClick,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }

                        ),
                        text = "Click to track your period\n" + "\u2304",
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.raleway_bolditalic)),
                        fontSize = 14.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                blurRadius = 1f
                            )
                        ),
                        color = Color(0xFF1822EE),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}


/*
    drawIntoCanvas { canvas ->
                    val paint = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        textSize = 20.sp.toPx()
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.LEFT
                    }

                    (0..30).forEach {
                        val step = it.toFloat() * totalSweep/(31f)
                        val path = Path().apply {
                            addArc(
                                RectF(
                                    center.x - radius + offset,
                                    center.y - radius + offset,
                                    center.x + radius - offset,
                                    center.y + radius - offset
                                ),
                                startAngle + step,
                                totalSweep/31f + step,
                            )
                        }
                        canvas.nativeCanvas.drawTextOnPath(it.toString() , path, 0f, 20f, paint)
                    }

                }

                This kinda works for numbers
 */

