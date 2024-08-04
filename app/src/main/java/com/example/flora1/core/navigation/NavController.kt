package com.example.flora1.core.navigation

import androidx.navigation.NavController


fun NavController.popAllPreviousDestinations(){
    do{
        val wasPopped = popBackStack()
    }while (wasPopped)
}
