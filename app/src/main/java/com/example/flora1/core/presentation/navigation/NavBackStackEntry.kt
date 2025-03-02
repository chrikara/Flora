package com.example.flora1.core.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavBackStackEntry

/*
See Ian Lake's answer:

https://slack-chats.kotlinlang.org/t/507821/so-i-did-this-and-i-hate-it-how-do-you-properly-return-resul

 */

@SuppressLint("ComposableNaming")
@Composable
fun <T> NavBackStackEntry.performActionOnce(
    savedStateKey: String,
    action: (T?) -> Unit
) {
    val savedStateHandle = savedStateHandle

    val id by savedStateHandle
        .getLiveData<T>(savedStateKey)
        .observeAsState(null)

    if (id != null) {
        LaunchedEffect(key1 = id) {
            try {
                action(id)
            } finally {
                savedStateHandle.remove<T>(savedStateKey)
            }
        }
    }
}
