package com.example.flora1.domain.mydoctors.model

import java.time.LocalDateTime
import java.util.UUID

data class Doctor(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val status: DoctorStatus,
    val lastUpdated: LocalDateTime,
    val image: String? = null,
)
