package com.example.flora1.navigationroot.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.flora1.core.presentation.ui.components.LoginRoot
import com.example.flora1.features.onboarding.GetStartedRoot
import com.example.flora1.features.onboarding.MinorAgeRoot
import com.example.flora1.features.onboarding.averagecycle.AverageCycleRoot
import com.example.flora1.features.onboarding.born.BornRoot
import com.example.flora1.features.onboarding.contraceptives.ContraceptivesRoot
import com.example.flora1.features.onboarding.gynosurgery.GynosurgeryRoot
import com.example.flora1.features.onboarding.lastperiod.LastPeriodRoot
import com.example.flora1.features.onboarding.medvits.MedVitsRoot
import com.example.flora1.features.onboarding.pregnancy.PregnancyRoot
import com.example.flora1.features.onboarding.pregnancystats.PregnancyStatsRoot
import com.example.flora1.features.onboarding.race.RaceRoot
import com.example.flora1.features.onboarding.sleepqualitytilllastperiod.SleepQualityTillLastPeriodRoot
import com.example.flora1.features.onboarding.stresstilllastperiod.StressTillLastPeriodRoot
import com.example.flora1.features.onboarding.usernameage.HeightRoot
import com.example.flora1.features.onboarding.weight.WeightRoot
import com.example.flora1.navigationroot.Screen
import com.example.flora1.navigationroot.main.databaseRef
import com.example.flora1.navigationroot.main.incrementClick

fun NavGraphBuilder.onBoardingNavigationRoot(
    navController: NavController,
) {

    composable<Screen.LoginOnBoarding> {
        LoginRoot(
            onSuccessfulLogin = {
                databaseRef.incrementClick("navigateToBorn")
                navController.navigate(Screen.Born)
            },
            onContinueAsAnonymous = {
                databaseRef.incrementClick("navigateToBorn")
                navController.navigate(Screen.Born)
            },
            onBackClicked = null
        )
    }

    composable<Screen.Born> {
        BornRoot(
            onNext = { isEligibleForFlora ->
                if (isEligibleForFlora) {
                    databaseRef.incrementClick("navigateToHeight")
                    navController.navigate(Screen.Height)

                } else {
                    databaseRef.incrementClick("navigateToMinorAge")
                    navController.navigate(Screen.MinorAge)
                }
            }
        )
    }

    composable<Screen.MinorAge> {
        MinorAgeRoot(onBack = {
            navController.navigateUp()
        })
    }

    composable<Screen.Height> {
        HeightRoot(
            onNext = {
                databaseRef.incrementClick("navigateToWeight")
                navController.navigate(Screen.Weight)
            }
        )
    }

    composable<Screen.Weight> {
        WeightRoot(
            onNext = {
                databaseRef.incrementClick("navigateToPregnancy")
                navController.navigate(Screen.Pregnancy)
            }
        )
    }

    composable<Screen.Pregnancy> {
        PregnancyRoot(
            onNext = { hasBeenPregnant ->
                if (hasBeenPregnant) {
                    databaseRef.incrementClick("navigateToPregnancyStats")
                    navController.navigate(Screen.PregnancyStats)

                } else {
                    databaseRef.incrementClick("navigateToRace")
                    navController.navigate(Screen.Race)
                }
            }
        )
    }

    composable<Screen.PregnancyStats> {
        PregnancyStatsRoot(
            onNext = {
                databaseRef.incrementClick("navigateToRace")
                navController.navigate(Screen.Race)
            },
            onBack = navController::navigateUp,
        )
    }

    composable<Screen.Race> {
        RaceRoot(
            onNext = {
                databaseRef.incrementClick("navigateMedVits")
                navController.navigate(Screen.MedVits)
            },
        )
    }

    composable<Screen.MedVits> {
        MedVitsRoot(
            onNext = {
                databaseRef.incrementClick("navigateToGynosurgery")
                navController.navigate(Screen.Gynecosurgery)
            },
        )
    }

    composable<Screen.Gynecosurgery> {
        GynosurgeryRoot(
            onNext = {
                databaseRef.incrementClick("navigateToContraceptives")
                navController.navigate(Screen.Contraceptives)
            },
        )
    }

    composable<Screen.Contraceptives> {
        ContraceptivesRoot(
            onNext = {
                databaseRef.incrementClick("navigateToStress")
                navController.navigate(Screen.StressLevelTillLastPeriod)
            },
        )
    }

    composable<Screen.StressLevelTillLastPeriod> {
        StressTillLastPeriodRoot(
            onNext = {
                databaseRef.incrementClick("navigateToSleep")
                navController.navigate(Screen.SleepQualityTillLastPeriod)
            },
        )
    }

    composable<Screen.SleepQualityTillLastPeriod> {
        SleepQualityTillLastPeriodRoot(
            onNext = {
                databaseRef.incrementClick("navigateToAverageCycle")
                navController.navigate(Screen.AverageCycle)
            },
            onBack = navController::navigateUp,
        )
    }

    composable<Screen.AverageCycle> {
        AverageCycleRoot(
            onNext = {
                databaseRef.incrementClick("navigateToLastPeriod")
                navController.navigate(Screen.LastPeriod)
            },
        )
    }

    composable<Screen.LastPeriod> {
        LastPeriodRoot(
            onNext = {
                databaseRef.incrementClick("navigateToGetStarted")
                navController.navigate(Screen.GetStarted)
            },
        )
    }

    composable<Screen.GetStarted> {
        GetStartedRoot(
            onPrimaryClicked = {
                databaseRef.incrementClick("navigateToMainScreen")
                navController.popBackStack(Screen.LoginOnBoarding, inclusive = true)
                navController.navigate(Screen.Main)
            },
            onSecondaryClicked = {
                databaseRef.incrementClick("navigateAllTheWayBackToOnBoarding")
                navController.popBackStack(Screen.Born, inclusive = false)
            }
        )
    }
}
