package com.example.flora1.core.domain.util

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: RootError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return map {  }
}

typealias EmptyDataResult<E> = Result<Unit, E>
