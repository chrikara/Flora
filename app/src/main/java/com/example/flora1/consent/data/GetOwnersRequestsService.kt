package com.example.flora1.consent.data

import com.example.flora1.consent.data.model.OwnerResponse
import com.example.flora1.core.network.ConsentApi
import com.example.flora1.core.network.get
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine

interface GetOwnersRequestsService {
    suspend fun getOwnersResponses(
        ownerAddress: String,
    ): Result<List<OwnerResponse>, DataError.Network>
}

class DefaultGetOwnersRequestsService(
    override val httpClientEngine: HttpClientEngine? = null,
) : GetOwnersRequestsService, ConsentApi {

    override suspend fun getOwnersResponses(
        ownerAddress: String,
    ): Result<List<OwnerResponse>, DataError.Network> =
        get(
            endpoint = "/owners/$ownerAddress/requests",
        )
}
