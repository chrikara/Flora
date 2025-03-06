package com.example.flora1.core.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.util.Error
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.onIsSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> Flow<T>.stateIn(
    viewModel: ViewModel,
    initialValue: T
): StateFlow<T> =
    stateIn(viewModel.viewModelScope, SharingStarted.WhileSubscribed(5000L), initialValue)

@Composable
fun <T : Any, E : Error> Flow<Result<T, E>>.collectSuccessAsState(
    initial: T,
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> = produceState(initial, this, context) {
    if (context == EmptyCoroutineContext) {
        collect { status ->
            status.onIsSuccess { value = it }
        }
    } else withContext(context) {
        collect { status ->
            status.onIsSuccess { value = it }
        }
    }
}
