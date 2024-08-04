package com.example.flora1.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xCC625B71)
val Pink40 = Color(0xFF7D5260)

val Orange100 = Color(0xFFF33168)
val Orange60 = Color(0x99F33168)
val Gray80 = Color(0xB3FFFFFF)
val TertiaryGray = Color(0xFF949494)

val GrayColorOnBox = Color(0xFF616161)

val PrimaryVerticalBrush = Brush.verticalGradient(
    listOf<Color>(
        Orange100,
        Color.Transparent
    )
)

val PrimaryHorizontalBrush = Brush.horizontalGradient(
    listOf<Color>(
        Orange100,
        Orange60
    )
)

fun conditionalPrimaryBrush(enabled: Boolean) = if (enabled)
    PrimaryHorizontalBrush
else
    Brush.linearGradient(listOf(PurpleGrey40, PurpleGrey80))

val disableBrush = Brush.linearGradient(listOf(PurpleGrey40, PurpleGrey80))
