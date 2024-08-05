package com.example.flora1.core.date

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Long.toDate(): LocalDate = Instant.ofEpochMilli(this).atZone(
    ZoneId.systemDefault()
).toLocalDate()

fun performActionBetweenTwoDates(
    startingDateInLong: Long,
    endingDateInLong: Long? = null,
    includeLastDay: Boolean = true,
    action: (currentDate: LocalDate) -> Unit,
) {
    val startingDate = startingDateInLong.toDate()
    val endingDate = endingDateInLong?.toDate()

    performActionBetweenTwoDates(
        startingDate = startingDate,
        endingDate = endingDate,
        includeLastDay = includeLastDay,
        action = action,
    )
}

fun performActionBetweenTwoDates(
    startingDate: LocalDate,
    endingDate: LocalDate? = null,
    includeLastDay: Boolean = true,
    action: (currentDate: LocalDate) -> Unit,
) {
    if (endingDate == null) {
        action(startingDate)
        return
    }

    if (!startingDate.isBefore(endingDate)) {
        throw IllegalArgumentException("Start date should not be bigger than ending date")
    }

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
