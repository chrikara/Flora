package com.example.flora1.domain.personaldetails

import com.example.flora1.features.onboarding.weight.WeightViewModel.Companion.MAX_WEIGHT_CHARS

interface WeightValidator {
    fun isWeightValid(weight: String): Boolean
}

class DefaultWeightValidator : WeightValidator {
    override fun isWeightValid(weight: String): Boolean {
        if (weight.isEmpty()) return true

        return (weight.hasAtMostOneDot() &&
                weight.isOnlyDigitsMinusDots() &&
                weight.hasLessThanMaxChars()) && !weight.startsWith('0')
    }

    private fun String.hasAtMostOneDot() = count { it == '.' } <= 1
    private fun String.isOnlyDigitsMinusDots() = replace(".", "").all { it.isDigit() }
    private fun String.hasLessThanMaxChars() = length <= MAX_WEIGHT_CHARS
}
