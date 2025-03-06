package com.example.flora1.consent.data

import com.example.flora1.consent.domain.HasGivenConsentUseCase
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.map

internal class ConsentApiHasGivenConsentUseCase(
    private val getOwnersService: GetOwnersService,
) : HasGivenConsentUseCase {
    override suspend fun hasGivenConsent(selectedAddress: String): Result<Boolean, DataError.Network> =
        getOwnersService.getOwners().map { selectedAddress in it }


}
