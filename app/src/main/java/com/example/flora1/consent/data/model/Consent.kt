package com.example.flora1.consent.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Consent(
    val dataOwner: String,
    val dataSeeker: String,
    val signature: String,
)
