package com.example.flora1.consent.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    @SerialName("tokenId")
    val id: Int,

    @SerialName("status")
    val status: Int,

    @SerialName("dataSeeker")
    val name: String,

    @SerialName("updatedAt")
    val updatedAt: String,
)
