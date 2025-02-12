package com.example.flora1.domain.db

import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.db.toPeriodEntity
import com.example.flora1.domain.db.model.Period

class SavePeriodUseCase(
    private val db: PeriodDatabase,
) {
    suspend fun savePeriod(
        period: Period,
    ) =
        db.dao().savePeriodEntry(
            period = period.toPeriodEntity(),
        )
}
