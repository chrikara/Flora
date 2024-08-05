package com.example.flora1

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
        startDestination = Screen.Main.name,
    ) {

        composable(Screen.Splash.name) {
            SplashScreenRoot(
                onFinishedAnimation = {
                    navController.popBackStack()
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
            MinorAgeRoot(onBack = { navController.popBackStack() })
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
                onTextPeriodTrackClick = { navController.navigate(Screen.Calendar.name) }
            )
        }

        composable(Screen.Calendar.name) {
            CalendarRoot()
        }
    }
}

