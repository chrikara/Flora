package com.example.flora1.data.auth

import com.example.flora1.core.network.FloraApi
import com.example.flora1.core.network.post
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.auth.toToken
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.map
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.firstOrNull

class DefaultRefreshService(
    override val httpClientEngine: HttpClientEngine? = null,
    private val preferences: Preferences2,
) : RefreshService, FloraApi {
    override val contentType: ContentType
        get() = ContentType.Application.Json

    override suspend fun refreshToken(): Result<LoginResponse, DataError.Network> =
        post<Unit, LoginResponse>(
            endpoint = "/refreshToken",
            headers = mapOf(
                HttpHeaders.Authorization to "Bearer ${preferences.token.firstOrNull() ?: ""}",
            )
        ).map {
            preferences.saveToken(it.toToken())
            it
        }

}
