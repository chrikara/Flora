package com.example.flora1.navigationroot.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
import com.example.flora1.features.onboarding.usernameage.UsernameAgeRoot
import com.example.flora1.features.onboarding.weight.WeightRoot
import com.example.flora1.navigationroot.Screen

fun NavGraphBuilder.onBoardingNavigationRoot(
    navController: NavController,
) {

    composable(Screen.UsernameAge.name) {
        UsernameAgeRoot(
            onNext = {
                navController.navigate(Screen.Born.name)
            }
        )
    }

    composable(Screen.Born.name) {
        BornRoot(
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
        )
    }

    composable(Screen.MedVits.name) {
        MedVitsRoot(
            onNext = {
                navController.navigate(Screen.Gynecosurgery.name)
            },
        )
    }

    composable(Screen.Gynecosurgery.name) {
        GynosurgeryRoot(
            onNext = {
                navController.navigate(Screen.Contraceptives.name)
            },
        )
    }

    composable(Screen.Contraceptives.name) {
        ContraceptivesRoot(
            onNext = {
                navController.navigate(Screen.StressLevelTillLastPeriod.name)
            },
        )
    }

    composable(Screen.StressLevelTillLastPeriod.name) {
        StressTillLastPeriodRoot(
            onNext = {
                navController.navigate(Screen.SleepQualityTillLastPeriod.name)
            },
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
        )
    }

    composable(Screen.LastPeriod.name) {
        LastPeriodRoot(
            onNext = { navController.navigate(Screen.GetStarted.name) },
        )
    }

    composable(Screen.GetStarted.name) {
        GetStartedRoot(
            onPrimaryClicked = {
                /*
                Will have to test if popBackStack overloads work
                 */
                navController.popBackStack(Screen.UsernameAge.name, inclusive = true)
                navController.navigate(Screen.Main.name)
            },
            onSecondaryClicked = {
                navController.popBackStack(Screen.UsernameAge.name, inclusive = false)
            }
        )
    }
}
