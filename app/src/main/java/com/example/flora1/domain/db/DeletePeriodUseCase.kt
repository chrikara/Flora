package com.example.flora1.domain.db

import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.db.toPeriodEntity
import com.example.flora1.domain.db.model.Period

class DeletePeriodUseCase(
    private val db: PeriodDatabase,
) {
    suspend fun deletePeriod(
        period: Period,
    ) =
        db.dao().deletePeriodEntry(
            period = period.toPeriodEntity(),
        )
}
