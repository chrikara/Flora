package com.example.flora1.consent.data

import com.example.flora1.consent.data.model.Consent
import com.example.flora1.core.network.ConsentApi
import com.example.flora1.core.network.post
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine

interface GrantConsentService {
    suspend fun grant(
        tokenId: Int,
        dataOwner: String,
        dataSeeker: String,
        signature: String,
    ): Result<Unit, DataError.Network>
}

class DefaultGrantConsentService(
    override val httpClientEngine: HttpClientEngine? = null,
) : GrantConsentService, ConsentApi {
    override suspend fun grant(
        tokenId: Int,
        dataOwner: String,
        dataSeeker: String,
        signature: String
    ): Result<Unit, DataError.Network> =
        post(
            endpoint = "/consents/$tokenId/grant",
            body = Consent(
                dataOwner = dataOwner,
                dataSeeker = dataSeeker,
                signature = signature,
            )
        )
}

