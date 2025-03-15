package com.example.flora1.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<STATE, ACTION, EVENT> : ViewModel() {
    abstract val state: STATE
    abstract fun onAction(action: ACTION)

    private val _events = Channel<EVENT>()
    val events = _events.receiveAsFlow()

    suspend fun send(event: EVENT) {
        _events.send(event)
    }
}
