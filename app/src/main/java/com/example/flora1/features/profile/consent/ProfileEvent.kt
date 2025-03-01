package com.example.flora1.features.profile.consent

sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
    data object NavigateToManageConsent : ProfileEvent
    data object NavigateToMyDoctorsSuccess : ProfileEvent
    data object NavigateToMyDoctorsFailed : ProfileEvent
}
