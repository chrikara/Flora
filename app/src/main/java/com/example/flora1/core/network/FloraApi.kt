package com.example.flora1.core.network

import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface FloraApi : Api {
    override val baseUrl: String
        get() = "https://flora-api.com"
}

class GetPredictionsService(
    override val httpClientEngine: HttpClientEngine? = null,
) : FloraApi {
    suspend fun getPredictions(): Result<List<PeriodResponse>> = get(
        endpoint = "/predictions"
    )
}

@Serializable
data class PeriodResponse(
    @SerialName("day")
    val day: String? = null,

    @SerialName("month")
    val month: String? = null,

    @SerialName("year")
    val year: String? = null,
)
