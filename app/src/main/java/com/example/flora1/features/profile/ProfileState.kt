package com.example.flora1.features.profile

import com.example.flora1.features.onboarding.weight.PregnancyStatus

data class ProfileState(
    val pregnancyStatus: PregnancyStatus = PregnancyStatus.NO_COMMENT,
)
