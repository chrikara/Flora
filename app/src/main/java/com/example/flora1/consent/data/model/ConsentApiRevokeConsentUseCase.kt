package com.example.flora1.consent.data.model

import com.example.flora1.consent.data.RevokeConsentService
import com.example.flora1.consent.domain.RevokeConsentUseCase
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

internal class ConsentApiRevokeConsentUseCase(
    private val revokeConsentService: RevokeConsentService,
) : RevokeConsentUseCase {

    override suspend fun revoke(
        doctorId: Int,
        ownerAddress: String,
        doctorName: String,
        signature: String
    ): Result<Unit, DataError.Network> =
        revokeConsentService.revoke(
            tokenId = doctorId,
            dataOwner = ownerAddress,
            dataSeeker = doctorName,
            signature = signature,
        )
}
