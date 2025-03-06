package com.example.flora1.consent.domain

import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

interface GrantConsentUseCase {
    suspend fun grant(
        doctorId: Int,
        ownerAddress: String,
        doctorName: String,
        signature: String,
    ): Result<Unit, DataError.Network>
}
