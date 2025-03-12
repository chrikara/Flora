package com.example.flora1.consent.data

import com.example.flora1.consent.data.model.mappers.OwnerResponseToDoctorMapper
import com.example.flora1.consent.domain.GetDoctorsUseCase
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import com.example.flora1.domain.util.map

internal class ConsentApiGetDoctorsConsentUseCase(
    private val getOwnersRequestsService: GetOwnersRequestsService,
) : GetDoctorsUseCase {

    override suspend fun getDoctors(selectedAddress: String): Result<List<Doctor>, DataError.Network> =
        with(OwnerResponseToDoctorMapper) {
            getOwnersRequestsService.getOwnersResponses(
                ownerAddress = selectedAddress
            ).map { ownerResponses ->
                ownerResponses
                    .map { it.toDoctor() }
                    .sortedByDescending(Doctor::updatedAt)
            }
        }
}
