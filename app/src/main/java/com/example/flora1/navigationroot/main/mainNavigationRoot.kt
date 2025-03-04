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
import com.example.flora1.features.profile.consent.ManageConsentRoot
import com.example.flora1.features.profile.duthstats.DuthStatsRoot
import com.example.flora1.features.profile.mydoctors.MyDoctorsRoot
import com.example.flora1.navigationroot.Screen
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.google.firebase.database.database

val myDatabase =
    Firebase.database("https://luxo-baf4a-default-rtdb.europe-west1.firebasedatabase.app/")

val databaseRef =
    myDatabase.reference

fun DatabaseReference.incrementClick(key: String) =
    updateChildren(mapOf(key to ServerValue.increment(1)))

internal fun NavGraphBuilder.mainNavigationRoot(navController: NavController) {
    composable<Screen.Main> {
        MainRoot(
            onCalendarClick = {
                databaseRef.incrementClick("calendarClicked")
                navController.navigate(Screen.Calendar)
            },
            onProfileClick = {
                databaseRef.incrementClick("profileClicked")
                navController.navigate(Screen.Settings)
            }
        )
    }

    composable<Screen.Calendar>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        CalendarRoot(
            onNavigateBack = {
                databaseRef.incrementClick("calendarBack")
                navController.popBackStack()
            },
        )
    }

    composable<Screen.Settings>(
        enterTransition = { enterToLeft() },
        exitTransition = { exitFromLeft() },
    ) {
        ProfileRoot(
            onBack = navController::popBackStack,
            onNavigateToManageConsent = {
                databaseRef.incrementClick("navigateToManageConsent")
                navController.navigate(Screen.ManageConsent)
            },
            onNavigateToMyDoctors = {
                databaseRef.incrementClick("navigateToMyDoctors")
                navController.navigate(Screen.MyDoctors)
            },
            onNavigateToLogin = { id ->
                databaseRef.incrementClick("navigateToLogin")
                navController.navigate(Screen.LoginProfile(id))
            },
            onNavigateToDuthStats = {
                databaseRef.incrementClick("navigateDuthStats")
                navController.navigate(Screen.DuthStats)

            }
        )

        it.performActionOnce<Int>(savedStateKey = LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_KEY) { manageConsentId ->
            if (manageConsentId == LOGIN_FROM_DATA_MANAGE_CONSENT_RESULT_SUCCESS) {
                databaseRef.incrementClick("navigateToManageConsent")
                navController.navigate(Screen.ManageConsent)
            }
        }
    }

    composable<Screen.ManageConsent>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        ManageConsentRoot(
            onBack = {
                databaseRef.incrementClick("navigateBackFromManageConsent")
                navController.popBackStack()
            }
        )
    }

    composable<Screen.MyDoctors>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        MyDoctorsRoot(
            onBack = {
                databaseRef.incrementClick("navigateBackFromMyDoctors")
                navController.popBackStack()
            }
        )
    }

    composable<Screen.DuthStats>(
        enterTransition = { enterToRight() },
        exitTransition = { exitFromRight() },
    ) {
        DuthStatsRoot()
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
                databaseRef.incrementClick("navigateBackFromLogin")
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
