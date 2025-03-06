package com.example.flora1.consent.data

import com.example.flora1.consent.domain.GrantConsentUseCase
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

internal class ConsentApiGrantConsentUseCase(
    private val grantConsentService: GrantConsentService,
) : GrantConsentUseCase {

    override suspend fun grant(
        doctorId: Int,
        ownerAddress: String,
        doctorName: String,
        signature: String
    ): Result<Unit, DataError.Network> =
        grantConsentService.grant(
            tokenId = doctorId,
            dataOwner = ownerAddress,
            dataSeeker = doctorName,
            signature = signature,
        )
}
