package com.example.flora1.consent.domain

import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

interface HasGivenConsentUseCase {
    suspend fun hasGivenConsent(
        selectedAddress: String,
    ): Result<Boolean, DataError.Network>
}
