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
