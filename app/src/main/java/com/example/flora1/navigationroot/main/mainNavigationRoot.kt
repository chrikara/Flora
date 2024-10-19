package com.example.flora1.navigationroot.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.flora1.features.main.MainRoot
import com.example.flora1.features.onboarding.calendar.CalendarRoot
import com.example.flora1.navigationroot.Screen

internal fun NavGraphBuilder.mainNavigationRoot(navController: NavController) {

    composable(Screen.Main.name) {
        MainRoot(
            onCalendarClick = { navController.navigate(Screen.Calendar.name) },
            onTextPeriodTrackClick = { navController.navigate(Screen.Calendar.name) },
            onSettingsClick = {}
        )
    }

    composable(
        route = Screen.Calendar.name,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )

        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
    ) {
        CalendarRoot()
    }
}
