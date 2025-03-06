package com.example.flora1.domain.util

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.example.flora1.domain.util.Error>(val error: E) :
        Result<Nothing, E>

    data object Running : Result<Nothing, Nothing>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
        Result.Running -> Result.Running
    }
}

inline fun <T, E : Error> Result<T, E>.onIsSuccess(action: (value: T) -> Unit): Result<T, E> {
    if (this is Result.Success)
        action(data)
    return this
}

inline fun <T, E : Error> Result<T, E>.onIsFailure(action: (value: E) -> Unit): Result<T, E> {
    if (this is Result.Error)
        action(error)
    return this
}

val <T, E : Error> Result<T, E>.isRunning
    get() = this is Result.Running

val <T, E : Error> Result<T, E>.isError
    get() = this is Result.Error

fun <T, E : Error> Result<T, E>.getOrNull(): T? =
    when (this) {
        is Result.Error -> null
        is Result.Success -> data
        Result.Running -> null
    }

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

typealias EmptyResult<E> = Result<Unit, E>
