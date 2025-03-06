package com.example.flora1.consent.domain

import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

interface GetDoctorsUseCase {
    suspend fun getDoctors(
        selectedAddress: String,
    ): Result<List<Doctor>, DataError.Network>
}
