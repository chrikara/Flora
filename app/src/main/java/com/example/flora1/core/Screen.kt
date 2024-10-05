package com.example.flora1.core

sealed class Screen(val name : String) {

    data object Splash : Screen("splash")
    data object UsernameAge : Screen("usernameAge")
    data object Born : Screen("born")
    data object MinorAge : Screen("minorAge")
    data object AverageCycle : Screen("averageCycle")
    data object LastPeriod : Screen("lastPeriod")
    data object GetStarted : Screen("getStarted")
    data object Calendar : Screen("calendar")
    data object Main : Screen("main")
}
