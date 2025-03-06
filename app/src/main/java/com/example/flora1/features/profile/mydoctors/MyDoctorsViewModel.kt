package com.example.flora1.features.profile.mydoctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.flora1.consent.domain.GetDoctorsUseCase
import com.example.flora1.consent.domain.GrantConsentUseCase
import com.example.flora1.consent.domain.RevokeConsentUseCase
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.Result.Running
import com.example.flora1.domain.util.onIsFailure
import com.example.flora1.domain.util.onIsSuccess
import com.example.flora1.features.main.EthereumError
import com.example.flora1.features.main.EthereumWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MyDoctorsViewModel @Inject constructor(
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val grantConsentUseCase: GrantConsentUseCase,
    private val revokeConsentUseCase: RevokeConsentUseCase,
    private val ethereumWrapper: EthereumWrapper,
) : ViewModel() {

    private var _doctors = MutableStateFlow<Result<List<Doctor>, DataError.Network>>(Running)
    val doctors = _doctors.asStateFlow()

    val selectedAddress = ethereumWrapper.selectedAddress
        .asFlow()
        .filter(String::isNotBlank)
        .onEach { selectedAddress ->
            _doctors.update { getDoctorsUseCase.getDoctors(selectedAddress) }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            "",
        )

    private var _events = Channel<DoctorEvent>()
    val events = _events.receiveAsFlow()

    fun updateDoctorStatus(
        doctor: Doctor,
        newStatus: DoctorStatus,
    ) {
        if (doctor.status == newStatus)
            return

        ethereumWrapper.signIn { signatureResult ->
            viewModelScope.launch {
                signatureResult
                    .onIsSuccess { signature ->
                        grantOrRevoke(
                            doctor = doctor,
                            newStatus = newStatus,
                            signature = signature,
                        )
                        getDoctors()
                    }
                    .onIsFailure { error ->
                        handleEthereumError(error)
                    }
            }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            getDoctors()
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _events.send(DoctorEvent.NavigateBack)
        }
    }

    /*------------------------------------------------------------------*/

    private suspend fun grantOrRevoke(
        doctor: Doctor,
        newStatus: DoctorStatus,
        signature: String,
    ) {
        if (newStatus == DoctorStatus.GRANTED)
            grantConsentUseCase.grant(
                doctorId = doctor.id,
                ownerAddress = selectedAddress.value,
                doctorName = doctor.name,
                signature = signature,
            )
        else
            revokeConsentUseCase.revoke(
                doctorId = doctor.id,
                ownerAddress = selectedAddress.value,
                doctorName = doctor.name,
                signature = signature,
            )
    }

    private suspend fun handleEthereumError(error: EthereumError) {
        when (error) {
            EthereumError.DoctorRejected -> _events.send(DoctorEvent.DoctorRejected)
            EthereumError.NotConnectedToMetamask -> _events.send(DoctorEvent.SignInError)
            EthereumError.InvalidParameters -> _events.send(
                DoctorEvent.InvalidAddress(selectedAddress.value)
            )
        }
    }

    private suspend fun getDoctors() {
        _doctors.update { Running }
        _doctors.update { getDoctorsUseCase.getDoctors(selectedAddress = selectedAddress.value) }
    }

    companion object {
        val mockDoctors = listOf(
            Doctor(
                id = 1,
                name = "George",
                status = DoctorStatus.GRANTED,
                updatedAt = LocalDateTime.of(LocalDate.of(2023, 2, 15), LocalTime.of(23, 11))
            ),
            Doctor(
                id = 2,
                name = "Andreas Andreas Andreas Andreas Andreas Andreas Andreas Andreas Andreas Andreas ",
                status = DoctorStatus.REVOKED,
                updatedAt = LocalDateTime.of(LocalDate.of(2024, 2, 15), LocalTime.of(4, 33))
            ),
            Doctor(
                id = 3,
                name = "Christina",
                status = DoctorStatus.REVOKED,
                updatedAt = LocalDateTime.of(LocalDate.of(2025, 2, 15), LocalTime.of(5, 5))
            ),
            Doctor(
                id = 4,
                name = "Despoina",
                status = DoctorStatus.REQUESTED,
                updatedAt = LocalDateTime.of(LocalDate.of(2024, 4, 15), LocalTime.of(15, 33))
            ),
            Doctor(
                id = 5,
                name = "Georgia",
                status = DoctorStatus.GRANTED,
                updatedAt = LocalDateTime.of(LocalDate.of(2022, 12, 15), LocalTime.of(23, 33))
            ),
            Doctor(
                id = 6,
                name = "Antonis",
                status = DoctorStatus.REVOKED,
                updatedAt = LocalDateTime.of(LocalDate.of(2021, 11, 30), LocalTime.of(0, 33))
            ),
        )
    }
}
