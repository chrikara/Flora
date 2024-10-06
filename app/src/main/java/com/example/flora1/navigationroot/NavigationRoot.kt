package com.example.flora1.navigationroot

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.flora1.features.onboarding.contraceptives.ContraceptivesRoot
import com.example.flora1.features.onboarding.gynosurgery.GynosurgeryRoot
import com.example.flora1.features.onboarding.lastperiod.LastPeriodRoot
import com.example.flora1.features.onboarding.medvits.MedVitsRoot
import com.example.flora1.features.onboarding.pregnancystats.PregnancyStatsRoot
import com.example.flora1.features.onboarding.pregnancy.PregnancyRoot
import com.example.flora1.features.onboarding.race.RaceRoot
import com.example.flora1.features.onboarding.sleepqualitytilllastperiod.SleepQualityTillLastPeriodRoot
import com.example.flora1.features.onboarding.stresstilllastperiod.StressTillLastPeriodRoot
import com.example.flora1.features.onboarding.usernameage.HeightRoot
import com.example.flora1.features.onboarding.weight.WeightRoot
import com.example.flora1.features.onboarding.usernameage.UsernameAgeRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    viewModel: NavigationRootViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AverageCycle.name,
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
                    navController.navigate(Screen.Born.name)
                }
            )
        }

        composable(Screen.Born.name) {
            BornScreenRoot(
                onNext = { isEligibleForFlora ->
                    if (isEligibleForFlora)
                        navController.navigate(Screen.Height.name)
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

        composable(Screen.Height.name) {
            HeightRoot(
                onNext = {
                    navController.navigate(Screen.Weight.name)
                }
            )
        }

        composable(Screen.Weight.name) {
            WeightRoot(
                onNext = {
                    navController.navigate(Screen.Pregnancy.name)
                }
            )
        }

        composable(Screen.Pregnancy.name) {
            PregnancyRoot(
                onNext = { hasBeenPregnant ->
                    if (hasBeenPregnant)
                        navController.navigate(Screen.PregnancyStats.name)
                    else
                        navController.navigate(Screen.Race.name)
                }
            )
        }

        composable(Screen.PregnancyStats.name) {
            PregnancyStatsRoot(
                onNext = {
                    navController.navigate(Screen.Race.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.Race.name) {
            RaceRoot(
                onNext = {
                    navController.navigate(Screen.MedVits.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.MedVits.name) {
            MedVitsRoot(
                onNext = {
                    navController.navigate(Screen.Gynecosurgery.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.Gynecosurgery.name) {
            GynosurgeryRoot(
                onNext = {
                    navController.navigate(Screen.Contraceptives.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.Contraceptives.name) {
            ContraceptivesRoot(
                onNext = {
                    navController.navigate(Screen.StressLevelTillLastPeriod.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.StressLevelTillLastPeriod.name) {
            StressTillLastPeriodRoot(
                onNext = {
                    navController.navigate(Screen.SleepQualityTillLastPeriod.name)
                },
                onBack = navController::navigateUp,
            )
        }

        composable(Screen.SleepQualityTillLastPeriod.name) {
            SleepQualityTillLastPeriodRoot(
                onNext = {
                    navController.navigate(Screen.AverageCycle.name)
                },
                onBack = navController::navigateUp,
            )
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

