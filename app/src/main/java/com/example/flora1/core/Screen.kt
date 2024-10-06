package com.example.flora1.core

sealed class Screen(val name : String) {

    data object Splash : Screen("splash")
    data object UsernameAge : Screen("usernameAge")
    data object Height : Screen("height")
    data object Weight : Screen("weight")
    data object Pregnancy : Screen("pregnancy")
    data object PregnancyStats : Screen("pregnancyStats")
    data object Race : Screen("race")
    data object MedVits : Screen("medvits")
    data object Gynecosurgery : Screen("gynecosurgery")
    data object Contraceptives : Screen("contraceptives")
    data object Born : Screen("born")
    data object MinorAge : Screen("minorAge")
    data object AverageCycle : Screen("averageCycle")
    data object LastPeriod : Screen("lastPeriod")
    data object GetStarted : Screen("getStarted")
    data object Calendar : Screen("calendar")
    data object Main : Screen("main")
}
