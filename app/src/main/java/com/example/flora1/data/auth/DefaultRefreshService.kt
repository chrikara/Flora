package com.example.flora1.data.auth

import android.util.Log
import com.example.flora1.core.network.FloraApi
import com.example.flora1.core.network.post
import com.example.flora1.domain.Preferences
import com.example.flora1.domain.auth.toToken
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.map
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultRefreshService(
    override val httpClientEngine: HttpClientEngine? = null,
    private val preferences: Preferences,
) : RefreshService, FloraApi {
    override val contentType: ContentType
        get() = ContentType.Application.Json

    override suspend fun refreshToken(): Result<LoginResponse, DataError.Network> =
        post<Unit,LoginResponse>(
            endpoint = "/refreshToken",
            headers = mapOf(
                HttpHeaders.Authorization to "Bearer ${preferences.token}",
            )
        ).map {
            preferences.saveToken(it.toToken())
            it
        }

}
