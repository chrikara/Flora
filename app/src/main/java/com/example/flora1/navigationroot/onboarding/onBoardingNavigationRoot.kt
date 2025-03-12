package com.example.flora1.navigationroot.onboarding

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.flora1.core.presentation.activity.getActivity
import com.example.flora1.core.presentation.ui.components.LoginRoot
import com.example.flora1.features.onboarding.GetStartedRoot
import com.example.flora1.features.onboarding.MinorAgeRoot
import com.example.flora1.features.onboarding.averagecycle.AverageCycleRoot
import com.example.flora1.features.onboarding.born.BornRoot
import com.example.flora1.features.onboarding.contraceptives.ContraceptivesRoot
import com.example.flora1.features.onboarding.dark.DarkRoot
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

fun NavGraphBuilder.onBoardingNavigationRoot(
    navController: NavController,
) {


    composable<Screen.LoginOnBoarding> {
        LoginRoot(
            onSuccessfulLogin = {
                navController.navigate(Screen.Dark)
            },
            onContinueAsAnonymous = {
                navController.navigate(Screen.Dark)
            },
            onBackClicked = null
        )
    }

    composable<Screen.Dark> {
        val context = LocalContext.current
        val activity = remember {
            context.getActivity
        }

        DarkRoot(
            onNextClick = {
                navController.navigate(Screen.Born)
            },
            onBackClick = {
                if (navController.previousBackStackEntry == null && activity != null)
                    activity.finish()
                else
                    navController.popBackStack()
            }
        )
    }

    composable<Screen.Born> {
        BornRoot(
            onBack = navController::popBackStack,
            onNext = { isEligibleForFlora ->
                if (isEligibleForFlora)
                    navController.navigate(Screen.Height)
                else
                    navController.navigate(Screen.MinorAge)
            }
        )
    }

    composable<Screen.MinorAge> {
        MinorAgeRoot(onBack = navController::popBackStack)
    }

    composable<Screen.Height> {
        HeightRoot(
            onNext = {
                navController.navigate(Screen.Weight)
            },
            onBack = navController::popBackStack
        )
    }

    composable<Screen.Weight> {
        WeightRoot(
            onNext = {
                navController.navigate(Screen.Pregnancy)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.Pregnancy> {
        PregnancyRoot(
            onNext = { hasBeenPregnant ->
                if (hasBeenPregnant)
                    navController.navigate(Screen.PregnancyStats)
                else
                    navController.navigate(Screen.Race)
            },
            onBack = navController::popBackStack
        )
    }

    composable<Screen.PregnancyStats> {
        PregnancyStatsRoot(
            onNext = {
                navController.navigate(Screen.Race)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.Race> {
        RaceRoot(
            onNext = {
                navController.navigate(Screen.MedVits)
            },
            onBack = navController::popBackStack
        )
    }

    composable<Screen.MedVits> {
        MedVitsRoot(
            onNext = {
                navController.navigate(Screen.Gynecosurgery)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.Gynecosurgery> {
        GynosurgeryRoot(
            onNext = {
                navController.navigate(Screen.Contraceptives)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.Contraceptives> {
        ContraceptivesRoot(
            onNext = {
                navController.navigate(Screen.StressLevelTillLastPeriod)
            },
            onBack = navController::popBackStack
        )
    }

    composable<Screen.StressLevelTillLastPeriod> {
        StressTillLastPeriodRoot(
            onNext = {
                navController.navigate(Screen.SleepQualityTillLastPeriod)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.SleepQualityTillLastPeriod> {
        SleepQualityTillLastPeriodRoot(
            onNext = {
                navController.navigate(Screen.AverageCycle)
            },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.AverageCycle> {
        AverageCycleRoot(
            onNext = { navController.navigate(Screen.LastPeriod) },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.LastPeriod> {
        LastPeriodRoot(
            onNext = { navController.navigate(Screen.GetStarted) },
            onBack = navController::popBackStack,
        )
    }

    composable<Screen.GetStarted> {
        GetStartedRoot(
            onPrimaryClicked = {
                navController.popBackStack(Screen.LoginOnBoarding, inclusive = true)
                navController.navigate(Screen.Main)
            },
            onSecondaryClicked = {
                navController.popBackStack(Screen.Born, inclusive = false)
            }
        )
    }
}
