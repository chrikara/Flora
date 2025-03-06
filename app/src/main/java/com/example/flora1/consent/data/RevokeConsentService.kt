package com.example.flora1.consent.data

import com.example.flora1.consent.data.model.Consent
import com.example.flora1.core.network.ConsentApi
import com.example.flora1.core.network.post
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine

interface RevokeConsentService {
    suspend fun revoke(
        tokenId: Int,
        dataOwner: String,
        dataSeeker: String,
        signature: String,
    ): Result<Unit, DataError.Network>
}

class DefaultRevokeConsentService(
    override val httpClientEngine: HttpClientEngine? = null,
) : RevokeConsentService, ConsentApi {
    override suspend fun revoke(
        tokenId: Int,
        dataOwner: String,
        dataSeeker: String,
        signature: String
    ): Result<Unit, DataError.Network> =
        post(
            endpoint = "/consents/$tokenId/revoke",
            body = Consent(
                dataOwner = dataOwner,
                dataSeeker = dataSeeker,
                signature = signature,
            )
        )
}

