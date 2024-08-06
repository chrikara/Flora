package com.example.flora1.features.main.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flora1.R
import com.example.flora1.ui.theme.PrimaryHorizontalBrush
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PeriodSphere(
    onTextPeriodTrackClick: () -> Unit,
) {

    val context = LocalContext.current
    val density = LocalDensity.current

    var day by rememberSaveable {
        mutableStateOf(17)
    }

    var circlePositions: List<Pair<Float, Float>> = emptyList()


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

    val totalSweep = 300f
    val startAngle = 0f

    val radius = (screenWidthPx.toFloat()) / 2f
    val offsetFactor = 0.2f // Adjust this factor to change the offset

    val offset = radius * offsetFactor
    LaunchedEffect(key1 = Unit) {

        circlePositions = buildList {
            (1..30).forEach {
                add(
                    calculateCirclePosition(
                        day = it,
                        totalDays = 30,
                        totalSweep = totalSweep,
                        radius = radius - offset,
                        density = density,
                    )
                )
            }
        }.map {
            with(density) {
                Pair(it.first.toPx() + radius, it.second.toPx() + radius)
            }
        }
        circlePositions.forEachIndexed { index, it ->
            println("index $index " + Pair(it.first + radius, it.second + radius))
        }
    }

    val dayIndicatorSizeFloat = radius * 0.25f
    var dayIndicatorSize: Dp = with(density) {
        dayIndicatorSizeFloat.toDp()
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.6f)
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
                    .pointerInput(Unit) {
                        detectTapGestures { offsetClicked ->

                            circlePositions.forEachIndexed { index, position ->
                                if(abs(offsetClicked.x - position.first) < dayIndicatorSizeFloat/2f
                                    && abs(offsetClicked.y - position.second) < dayIndicatorSizeFloat/2f){
                                    day = index + 1
                                    return@forEachIndexed
                                }
                            }
                        }
                    }
            ) {

                drawCircle(
                    color = primaryColor,
                    center = this.center,
                    radius = radius,
                )

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
            }


            Box(
                modifier = Modifier
                    .width(innerCircleWidth)
                    .height(innerCircleWidth)
                    .clip(CircleShape)
                    .background(White),
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

            // Adjust this to change the position dynamically
            val totalDays = 30
            val (xPosition, yPosition) = calculateCirclePosition(
                day = day,
                totalDays = totalDays,
                totalSweep = totalSweep,
                radius = radius - offset,
                density = density,
            )

            Box(
                modifier = Modifier
                    .size(dayIndicatorSize)
                    .offset(
                        x = xPosition,
                        y = yPosition
                    )
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape,
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = day.toString(),
                    fontFamily = FontFamily(Font(R.font.opensans_regular)),
                    fontSize = 20.sp,

                    )

            }
        }
    }
}

fun calculateCirclePosition(
    day: Int,
    totalDays: Int,
    totalSweep: Float,
    radius: Float,
    density: Density
): Pair<Dp, Dp> {
    val sweepAnglePerDay = totalSweep / totalDays.toFloat()
    val angleInDegrees = 0 + sweepAnglePerDay * day
    val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

    val x = (radius * cos(angleInRadians)).toFloat()
    val y = (radius * sin(angleInRadians)).toFloat()

    return with(density) {
        Pair(x.toDp(), y.toDp())
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

