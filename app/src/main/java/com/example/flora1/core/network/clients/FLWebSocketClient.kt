package com.example.flora1.core.network.clients

import com.example.flora1.domain.Preferences2
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FLWebSocketClient @Inject constructor(
    private val preferences: Preferences2,
) : WebSocketClient() {

    override suspend fun urlString(): String =
        "ws://spartacus.ee.duth.gr:7093/ws?token=${preferences.token.firstOrNull()}"

    override val httpClient: HttpClient
        get() = HttpClientFactory.createForWebSockets()
}
