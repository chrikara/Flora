package com.example.flora1.features.profile.consent


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.flora1.consent.domain.HasGivenConsentUseCase
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import io.metamask.androidsdk.Ethereum
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ManageConsentViewModel @Inject constructor(
    private val hasGivenConsentUseCase: HasGivenConsentUseCase,
    private val ethereum: Ethereum,
) : ViewModel() {

    private var _hasGivenConsent =
        MutableStateFlow<Result<Boolean, DataError.Network>>(Result.Running)
    val hasGivenConsent = _hasGivenConsent.asStateFlow()

    val a = ethereum.ethereumState.asFlow()
        .map { it.selectedAddress }
        .onEach { selectedAddress ->
            _hasGivenConsent.update {
                hasGivenConsentUseCase.hasGivenConsent(selectedAddress)
            }
        }

    init {
        collectOwners()
    }

    private var job: Job? = null

    private fun collectOwners() {
        job?.cancel()
        job = ethereum.ethereumState.asFlow()
            .onStart { _hasGivenConsent.update { Result.Running } }
            .map { it.selectedAddress }
            .filter(String::isNotBlank)
            .onEach { selectedAddress ->
                _hasGivenConsent.update {
                    hasGivenConsentUseCase.hasGivenConsent(selectedAddress)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onRetry() {
        collectOwners()
    }

    fun onToggleDataConsent(newValue: Boolean) {
        /*
        Wait until we learn what to invoke for request
         */
    }
}
