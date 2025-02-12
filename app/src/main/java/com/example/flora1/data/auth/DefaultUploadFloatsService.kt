package com.example.flora1.data.auth

import com.example.flora1.core.network.FloraApi
import com.example.flora1.core.network.json
import com.example.flora1.core.network.post
import com.example.flora1.domain.Preferences
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString

class DefaultUploadFloatsService(
    override val httpClientEngine: HttpClientEngine? = null,
    private val preferences: Preferences,
) : UploadFloatsService, FloraApi {
    override val contentType: ContentType
        get() = ContentType.Application.Json


    override suspend fun uploadFloat(
        data: TrainingData,
    ): Result<String, DataError.Network> =
        post(
            endpoint = "upload-json-floats",
            body = MultiPartFormDataContent(
                formData {
                    val file = TextContent(
                        text = json.encodeToString(data),
                        contentType = ContentType.Application.Json,
                    )

                    append("description", "Ktor logo")
                    append("file", file.bytes() , Headers.build {
                        append(HttpHeaders.ContentType, "application/json")
                        append(HttpHeaders.ContentDisposition, "filename=\"file2\"")
                    })
                }
            ),
            headers = mapOf(
                HttpHeaders.Authorization to "Bearer ${preferences.token}",
            ),
        )
}
