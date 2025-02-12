package com.example.flora1.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flora1.domain.db.model.Period
import java.time.LocalDate


@Entity(tableName = "period")
data class PeriodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val flow: String,
    val day: Int,
    val month: Int,
    val year: Int,
)

fun PeriodEntity.toPeriod() =
    Period(
        id = id,
        date = LocalDate.of(year, month, day)
    )

fun Period.toPeriodEntity() =
    PeriodEntity(
        id = id,
        flow = "heavy",
        month = date.monthValue,
        day = date.dayOfMonth,
        year = date.year,
    )
