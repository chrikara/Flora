package com.example.flora1.domain.db.model

import java.time.LocalDate

data class Period(
    val id : Int = 0,
    val date: LocalDate,
){
    override fun equals(other: Any?): Boolean {
        return other is Period && other.date == date
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }
}

