package com.example.flora1.features.profile.consent

sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
    data object NavigateToManageConsent : ProfileEvent
    data object NavigateToMyDoctorsSuccess : ProfileEvent
    data class NavigateToLogin(val id: String) : ProfileEvent
    data object LogoutSuccessful : ProfileEvent
    data object NavigateToMyDoctorsFailed : ProfileEvent
}
