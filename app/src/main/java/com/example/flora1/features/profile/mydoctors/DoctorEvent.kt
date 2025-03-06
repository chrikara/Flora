package com.example.flora1.features.profile.mydoctors

sealed interface DoctorEvent {
    data object NavigateBack : DoctorEvent
    data object SignInError : DoctorEvent
    data object DoctorRejected : DoctorEvent
    data class InvalidAddress(val selectedAddress : String) : DoctorEvent
}
