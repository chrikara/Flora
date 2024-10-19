package com.example.flora1.navigationroot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flora1.features.onboarding.SplashScreenRoot
import com.example.flora1.navigationroot.main.mainNavigationRoot
import com.example.flora1.navigationroot.onboarding.onBoardingNavigationRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    viewModel: NavigationRootViewModel = hiltViewModel(),
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = Screen.Splash.name,
    ) {

        composable(Screen.Splash.name) {
            SplashScreenRoot(
                onFinishedAnimation = {
                    navController.popBackStack()
                    navController.navigate(
                        if (viewModel.shouldShowOnBoarding)
                            Screen.UsernameAge.name
                        else
                            Screen.Main.name
                    )
                }
            )
        }

        onBoardingNavigationRoot(navController = navController)
        mainNavigationRoot(navController = navController)

    }
}

