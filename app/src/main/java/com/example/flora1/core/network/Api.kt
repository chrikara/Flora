package com.example.flora1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlin.coroutines.cancellation.CancellationException

interface Api {
    val baseUrl: String
    val httpClientEngine: HttpClientEngine?
        get() = null
}

private data class HttpClientKey(
    val baseUrl: String,
    val httpClientEngine: HttpClientEngine?,
)

private val httpClients = mutableMapOf<HttpClientKey, HttpClient>()

val Api.httpClient: HttpClient
    get() = httpClients.getOrPut(HttpClientKey(baseUrl, httpClientEngine)) {
        createHttpClient(baseUrl)
    }

suspend inline fun <reified T : Any, reified R> Api.post(
    endpoint: String,
    queryParams: Map<String, String> = emptyMap(),
    multiQueryParams: Map<String, Iterable<String>> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
    body: T,
): Result<R> = safeCall {
    httpClient.post(endpoint) {
        setQueryParams(
            queryParams = queryParams,
            multiQueryParams = multiQueryParams,
        )
        setHeaders(headers)
        setBody(body)
    }.body()
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T> {
    val response = try {
        execute()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.failure(Exception())
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T> {
    return when (response.status.value) {
        in 200..299 -> Result.success(response.body<T>())
        else -> Result.failure(Exception())
    }
}

suspend inline fun <reified T, reified R> Api.put(
    endpoint: String,
    queryParams: Map<String, String> = emptyMap(),
    multiQueryParams: Map<String, Iterable<String>> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
    body: T,
): Result<R> = safeCall {
    httpClient.put(endpoint) {
        setQueryParams(
            queryParams = queryParams,
            multiQueryParams = multiQueryParams,
        )
        setHeaders(headers)
        setBody(body)
    }.body()
}


suspend inline fun <reified R> Api.get(
    endpoint: String,
    queryParams: Map<String, String> = emptyMap(),
    multiQueryParams: Map<String, Iterable<String>> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
): Result<R> = safeCall {
    httpClient.get(endpoint) {
        setQueryParams(
            queryParams = queryParams,
            multiQueryParams = multiQueryParams,
        )
        setHeaders(headers)
    }.body()
}


suspend inline fun <reified R> Api.delete(
    endpoint: String,
    queryParams: Map<String, String> = emptyMap(),
    multiQueryParams: Map<String, Iterable<String>> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
): Result<R> = safeCall {
    httpClient.delete(endpoint) {
        setQueryParams(
            queryParams = queryParams,
            multiQueryParams = multiQueryParams,
        )
        setHeaders(headers)
    }.body()
}


fun HttpRequestBuilder.setQueryParams(
    queryParams: Map<String, String> = emptyMap(),
    multiQueryParams: Map<String, Iterable<String>> = emptyMap(),
) {
    url {
        queryParams.forEach { (name, value) ->
            parameters.append(name, value)
        }
        multiQueryParams.forEach { (name, values) ->
            values.forEach { value ->
                parameters.append(name, value)
            }
        }
    }
}


fun HttpRequestBuilder.setHeaders(
    headers: Map<String, String> = emptyMap(),
) {
    headers {
        headers.forEach { (name, value) ->
            append(name, value)
        }
    }
}

