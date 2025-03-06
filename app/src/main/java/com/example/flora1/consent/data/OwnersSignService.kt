package com.example.flora1.consent.data

import com.example.flora1.core.network.ConsentApi
import com.example.flora1.core.network.post
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface OwnersSignService {
    suspend fun sign(
        ownerAddress: String,
        signature: String,
        kyc: String,
    ): Result<Unit, DataError.Network>
}

class DefaultOwnersSignService(
    override val httpClientEngine: HttpClientEngine? = null,
) : OwnersSignService, ConsentApi {
    override suspend fun sign(
        ownerAddress: String,
        signature: String,
        kyc: String
    ): Result<Unit, DataError.Network> =
        post(
            endpoint = "/owners/$ownerAddress/sign",
            body = OwnerSignBody(
                signature = signature,
                kyc = kyc,
            )
        )
}

@Serializable
data class OwnerSignBody(
    @SerialName("signature")
    val signature: String,

    @SerialName("kyc")
    val kyc: String,

    )

