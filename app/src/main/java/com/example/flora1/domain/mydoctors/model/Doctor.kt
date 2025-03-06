package com.example.flora1.domain.mydoctors.model

import java.time.LocalDateTime

data class Doctor(
    val id: Int,
    val name: String,
    val status: DoctorStatus?,
    val updatedAt: LocalDateTime,
)
