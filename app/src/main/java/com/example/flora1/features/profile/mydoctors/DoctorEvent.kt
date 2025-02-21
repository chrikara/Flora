package com.example.flora1.features.profile.mydoctors

sealed interface DoctorEvent {
    data object NavigateBack : DoctorEvent
}
