package com.example.flora1.core.presentation.ui.navigation

import androidx.navigation.NavController
import com.example.flora1.core.Screen


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
