package com.example.flora1.domain.mydoctors.model

enum class DoctorStatus {
    REQUESTED, GRANTED, REVOKED;

    companion object {
        val uiDoctorStatuses = entries
            .filter { it != REQUESTED }
            .toTypedArray()
    }
}
