package com.example.flora1

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flora1.core.Screen
import com.example.flora1.core.navigation.navigateAndPopUpTo
import com.example.flora1.core.navigation.popAllPreviousDestinations
import com.example.flora1.features.main.MainRoot
import com.example.flora1.features.onboarding.GetStartedRoot
import com.example.flora1.features.onboarding.MinorAgeRoot
import com.example.flora1.features.onboarding.SplashScreenRoot
import com.example.flora1.features.onboarding.averagecycle.AverageCycleRoot
import com.example.flora1.features.onboarding.born.BornScreenRoot
import com.example.flora1.features.onboarding.calendar.CalendarRoot
import com.example.flora1.features.onboarding.lastperiod.LastPeriodRoot
import com.example.flora1.features.onboarding.usernameage.HeightRoot
import com.example.flora1.features.usernameage.UsernameAgeRoot


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
//    LaunchedEffect(key1 = navController) {
//        navController.currentBackStackEntryFlow.collectLatest {
//            /*
//            Εκτελείται κάθε φορά που αλλάζει navigation root
//             */
//        }
//    }

    NavHost(
        navController = navController,
        startDestination = Screen.Height.name,
    ) {

        composable(Screen.Splash.name) {
            SplashScreenRoot(
                onFinishedAnimation = {
                    navController.popBackStack()
                    navController.navigate(Screen.UsernameAge.name)
                }
            )
        }

        composable(Screen.UsernameAge.name) {
            UsernameAgeRoot(
                onNext = {
                    navController.navigate(Screen.Height.name)
                }
            )
        }

        composable(Screen.Height.name) {
            HeightRoot(
                onNext = {
                    navController.navigate(Screen.Born.name)
                }
            )
        }

        composable(Screen.Born.name) {
            BornScreenRoot(
                onNext = { isEligibleForFlora ->
                    if (isEligibleForFlora)
                        navController.navigate(Screen.AverageCycle.name)
                    else
                        navController.navigate(Screen.MinorAge.name)
                }
            )
        }

        composable(Screen.MinorAge.name) {
            MinorAgeRoot(onBack = {
                navController.navigateUp()
            })
        }

        composable(Screen.AverageCycle.name) {
            AverageCycleRoot(
                onNext = { navController.navigate(Screen.LastPeriod.name) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.LastPeriod.name) {
            LastPeriodRoot(
                onNext = { navController.navigate(Screen.GetStarted.name) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Screen.GetStarted.name) {
            GetStartedRoot(
                onPrimaryClicked = {
                    navController.popAllPreviousDestinations()
                    navController.navigate(Screen.Main.name)
                },
                onSecondaryClicked = {
                    navController.navigateAndPopUpTo(route = Screen.Born.name)
                }
            )
        }

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
}

