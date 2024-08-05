package com.example.flora1.core.date

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Long.toDate(): LocalDate = Instant.ofEpochMilli(this).atZone(
    ZoneId.systemDefault()
).toLocalDate()

fun performActionBetweenTwoDates(
    startingDate: LocalDate,
    endingDate: LocalDate,
    includeLastDay: Boolean = true,
    action: (currentDate: LocalDate) -> Unit,

    ) {
    var currentDate = startingDate
    do {
        action(currentDate)
        currentDate = currentDate.plusDays(1)
    } while (currentDate.isBefore(
            if (includeLastDay)
                endingDate.plusDays(1)
            else
                endingDate
        )
    )

}
