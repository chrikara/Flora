package com.example.flora1.core

sealed class Screen(val name : String) {

    data object OnBoarding : Screen("onBoarding")
    data object Splash : Screen("splash")
    data object Born : Screen("born")
    data object MinorAge : Screen("minorAge")
    data object AverageCycle : Screen("averageCycle")
}
