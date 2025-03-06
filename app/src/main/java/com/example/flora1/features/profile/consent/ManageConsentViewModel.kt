package com.example.flora1.features.profile.consent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.flora1.consent.data.OwnersSignService
import com.example.flora1.consent.domain.HasGivenConsentUseCase
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.onIsSuccess
import com.example.flora1.features.main.EthereumWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageConsentViewModel @Inject constructor(
    private val hasGivenConsentUseCase: HasGivenConsentUseCase,
    private val ownersSignService: OwnersSignService,
    private val preferences: Preferences2,
    private val ethereumWrapper: EthereumWrapper,
) : ViewModel() {
    private var _hasGivenConsentResult =
        MutableStateFlow<Result<Boolean, DataError.Network>>(Result.Running)
    val hasGivenConsentResult = _hasGivenConsentResult.asStateFlow()

    private val selectedAddress = ethereumWrapper
        .selectedAddress
        .asFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ""
        )

    init {
        collectOwners()
    }

    /*

     */

    private var job: Job? = null

    private fun collectOwners() {
        job?.cancel()
        job = hasGivenConsentUseCase
            .hasGivenConsent()
            .onEach { it ->
                _hasGivenConsentResult.value = it
            }
            .launchIn(viewModelScope)
    }

    fun onRetry() {
        collectOwners()
    }

    fun onToggleDataConsent() {
        ethereumWrapper.signIn { signatureResult ->
            viewModelScope.launch {
                signatureResult
                    .onIsSuccess { signature ->
                        ownersSignService.sign(
                            ownerAddress = selectedAddress.value,
                            signature = signature,
                            kyc = "xristos1@gmail.com",
                        )
                        collectOwners()
                    }
            }
        }

    }
}
