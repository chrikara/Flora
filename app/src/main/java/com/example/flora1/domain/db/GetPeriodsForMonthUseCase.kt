package com.example.flora1.domain.db

import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.db.PeriodEntity
import com.example.flora1.data.db.toPeriod
import com.example.flora1.domain.db.model.Period
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPeriodsForMonthUseCase(
    private val db: PeriodDatabase,
) {
    fun getPeriodsForMonth(
        month: Int,
        year: Int,
    ): Flow<Set<Period>> =
        db.dao().getPeriodLogsForMonth(
            specifiedMonth = month,
            specifiedYear = year,
        ).map { entities ->
            entities.map(PeriodEntity::toPeriod).toSet()
        }
}
