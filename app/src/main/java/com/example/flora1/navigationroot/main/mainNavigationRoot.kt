package com.example.flora1.navigationroot.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.flora1.core.presentation.navigation.performActionOnce
import com.example.flora1.core.presentation.ui.components.LoginRoot
import com.example.flora1.features.calendar.CalendarRoot
import com.example.flora1.features.main.MainRoot
import com.example.flora1.features.profile.ProfileRoot
import com.example.flora1.features.profile.ProfileViewModel
import com.example.flora1.features.profile.consent.ManageConsentContent
import com.example.flora1.features.profile.mydoctors.MyDoctorsContent
import com.example.flora1.navigationroot.Screen

internal fun NavGraphBuilder.mainNavigationRoot(navController: NavController) {
    composable<Screen.Main> {
        MainRoot(
            onCalendarClick = { navController.navigate(Screen.Calendar) },
            onProfileClick = { navController.navigate(Screen.Settings) }
        )
    }

    composable<Screen.Calendar>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        CalendarRoot(
            onNavigateBack = navController::popBackStack,
        )
    }

    composable<Screen.Settings>(
        enterTransition = { enterToLeft() },
        exitTransition = { exitFromLeft() },
    ) { backstackEntry ->
        ProfileRoot(
            onBack = navController::popBackStack,
            onNavigateToManageConsent = { navController.navigate(Screen.ManageConsent) },
            onNavigateToMyDoctors = { navController.navigate(Screen.MyDoctors) },
            onNavigateToLogin = { id ->
                navController.navigate(Screen.LoginProfile(id))
            }
        )

        backstackEntry.performActionOnce<Int>(savedStateKey = LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_KEY) { manageConsentId ->
            if (manageConsentId == LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_SUCCESS)
                navController.navigate(Screen.ManageConsent)
        }
    }

    composable<Screen.ManageConsent>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        ManageConsentContent(
            onBack = navController::popBackStack
        )
    }

    composable<Screen.MyDoctors>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        MyDoctorsContent(
            onBack = navController::popBackStack
        )
    }

    composable<Screen.LoginProfile>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) { backstackEntry ->

        val id = backstackEntry.toRoute<Screen.LoginProfile>().id

        LoginRoot(
            onSuccessfulLogin = {
                if (id == ProfileViewModel.MANAGE_DATA_CONSENT_ID)
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_KEY,
                        LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_SUCCESS,
                    )
                navController.popBackStack()
            },
            onContinueAsAnonymous = navController::popBackStack,
            onBackClicked = navController::popBackStack,
        )
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterToRight() = slideIntoContainer(
    animationSpec = tween(300, easing = EaseIn),
    towards = AnimatedContentTransitionScope.SlideDirection.Start
)

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitFromRight() = slideOutOfContainer(
    animationSpec = tween(300, easing = EaseOut),
    towards = AnimatedContentTransitionScope.SlideDirection.End
)

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterToLeft() = slideIntoContainer(
    animationSpec = tween(300, easing = EaseIn),
    towards = AnimatedContentTransitionScope.SlideDirection.End
)

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitFromLeft() =
    slideOutOfContainer(
        animationSpec = tween(300, easing = EaseOut),
        towards = AnimatedContentTransitionScope.SlideDirection.Start
    )

const val LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_SUCCESS = 14
const val LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_KEY = "manageDataConsent"
