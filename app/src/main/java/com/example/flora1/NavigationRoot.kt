package com.example.flora1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flora1.core.Screen
import com.example.flora1.features.AverageCycleRoot
import com.example.flora1.features.BornScreenRoot
import com.example.flora1.features.MinorAgeRoot
import com.example.flora1.features.SplashScreenRoot
import com.example.flora1.features.yearsToMillis


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.name,
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
                onNext = { dateSelected ->
                    if(isCurrentDateLessThanYears(dateSelected, 13))
                        navController.navigate(Screen.MinorAge.name)
                    else
                        navController.navigate(Screen.AverageCycle.name)
                }
            )
        }

        composable(Screen.MinorAge.name) {
            MinorAgeRoot(onBack = { navController.popBackStack() })
        }

        composable(Screen.AverageCycle.name) {
            AverageCycleRoot(onBack = { navController.popBackStack() })
        }
    }
}

private fun isCurrentDateLessThanYears(dateSelected: Long, years: Int)=
    System.currentTimeMillis() - dateSelected <= yearsToMillis(years = years)

