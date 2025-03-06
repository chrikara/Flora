package com.example.flora1.consent.data

import com.example.flora1.core.network.ConsentApi
import com.example.flora1.core.network.get
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine

interface GetOwnersService {
    suspend fun getOwners(): Result<List<String>, DataError.Network>
}

class DefaultGetOwnersService(
    override val httpClientEngine: HttpClientEngine? = null,
) : GetOwnersService, ConsentApi {
    override suspend fun getOwners(): Result<List<String>, DataError.Network> =
        get(
            endpoint = "/owners",
        )
}

