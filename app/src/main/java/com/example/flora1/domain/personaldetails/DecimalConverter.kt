package com.example.flora1.domain.personaldetails

interface DecimalConverter {
    fun Float.convertDecimalToString(): String
}

class DefaultDecimalConverter : DecimalConverter {
    override fun Float.convertDecimalToString(): String {
        if (this == 0f) return ""

        return if (this % 1 == 0f) {
            toInt().toString()
        } else {
            toString()
        }
    }
}


