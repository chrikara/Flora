package com.example.flora1.consent.data

import androidx.lifecycle.asFlow
import com.example.flora1.consent.domain.HasGivenConsentUseCase
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.map
import io.metamask.androidsdk.Ethereum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

internal class ConsentApiHasGivenConsentUseCase(
    private val getOwnersService: GetOwnersService,
    private val preferences: Preferences2,
    private val ethereum: Ethereum,
) : HasGivenConsentUseCase {
    override fun hasGivenConsent(): Flow<Result<Boolean, DataError.Network>> =
        combine(
            preferences.hasGivenDataConsent,
            ethereum.ethereumState.asFlow(),
        ) { hasGivenDataConsentPreference, ethereumState ->
            if (hasGivenDataConsentPreference == true)
                Result.Success(true)
            else {
                getOwnersService.getOwners()
                    .map { owners ->
                        ethereumState.selectedAddress.lowercase() in owners.map(String::lowercase)
                    }
            }
        }
            .distinctUntilChanged()
}

