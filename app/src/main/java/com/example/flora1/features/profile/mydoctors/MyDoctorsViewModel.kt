package com.example.flora1.features.profile.mydoctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MyDoctorsViewModel : ViewModel() {

    private var _doctors = MutableStateFlow(mockDoctors)
    val doctors = _doctors
        .map {
            it.sortedBy(Doctor::lastUpdated)
        }
        .map(List<Doctor>::reversed)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            _doctors.value,
        )

    private var _events = Channel<DoctorEvent>()
    val events = _events.receiveAsFlow()

    fun updateDoctorStatus(
        doctor: Doctor,
        newStatus: DoctorStatus,
    ) {
        _doctors.value = doctors.value.map {
            if (doctor == it)
                it.copy(status = newStatus)
            else
                it
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _events.send(DoctorEvent.NavigateBack)
        }
    }

    companion object {
        val mockDoctors = listOf(
            Doctor(
                name = "George",
                status = DoctorStatus.ACCEPTED,
                lastUpdated = LocalDateTime.of(LocalDate.of(2023, 2, 15), LocalTime.of(23, 11))
            ),
            Doctor(
                name = "Andreas",
                status = DoctorStatus.REVOKED,
                lastUpdated = LocalDateTime.of(LocalDate.of(2024, 2, 15), LocalTime.of(4, 33))
            ),
            Doctor(
                name = "Christina",
                status = DoctorStatus.DECLINED,
                lastUpdated = LocalDateTime.of(LocalDate.of(2025, 2, 15), LocalTime.of(5, 5))
            ),
            Doctor(
                name = "Despoina",
                status = DoctorStatus.ACCEPTED,
                lastUpdated = LocalDateTime.of(LocalDate.of(2024, 4, 15), LocalTime.of(15, 33))
            ),
        )
    }
}
