package com.example.flora1.consent.data.model.mappers

import com.example.flora1.consent.data.model.OwnerResponse
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus
import java.time.Instant
import java.time.ZoneId

object OwnerResponseToDoctorMapper {
    fun OwnerResponse.toDoctor(): Doctor =
        Doctor(
            id = id,
            name = name,
            status = when (status) {
                1 -> DoctorStatus.GRANTED
                3 -> DoctorStatus.REVOKED
                else -> DoctorStatus.REQUESTED
            },
            updatedAt = Instant.parse(updatedAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )
}
