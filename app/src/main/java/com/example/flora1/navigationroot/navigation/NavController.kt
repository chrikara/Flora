package com.example.flora1.navigationroot.navigation

import androidx.navigation.NavController


fun NavController.popAllPreviousDestinations(){
    do{
        val wasPopped = popBackStack()
    }while (wasPopped)
}

fun NavController.navigateAndPopUpTo(
    route : String,
    inclusive : Boolean = true
) = navigate(
    route = route,
    builder = {
        popUpTo(route){
            this.inclusive = inclusive
        }
    }
)
