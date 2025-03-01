package com.example.flora1.domain.personaldetails

import androidx.core.text.isDigitsOnly

interface HeightValidator {
    fun isHeightValid(height: String): Boolean
}

class DefaultHeightValidator : HeightValidator {
    override fun isHeightValid(height: String): Boolean {
        if (height.isEmpty()) return true

        return height.isDigitsOnly()
                && !height.startsWith('0')
                && height.length < 4
    }
}
