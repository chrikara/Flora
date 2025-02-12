package com.example.flora1.domain.db

import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.db.PeriodEntity
import com.example.flora1.data.db.toPeriod
import com.example.flora1.domain.db.model.Period
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllPeriodsUseCase(
    private val db: PeriodDatabase,
) {
    fun getAllPeriods(): Flow<Set<Period>> =
        db.dao().getAllPeriodLogs().map { entities ->
            entities
                .map(PeriodEntity::toPeriod)
                .toSet()
        }
}
