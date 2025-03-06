package com.example.flora1.consent.domain

import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface HasGivenConsentUseCase {
    fun hasGivenConsent(): Flow<Result<Boolean, DataError.Network>>
}
