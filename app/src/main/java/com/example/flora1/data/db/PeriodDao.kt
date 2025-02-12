package com.example.flora1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDao {


    @Upsert
    suspend fun savePeriodEntry(period: PeriodEntity)

    @Delete
    suspend fun deletePeriodEntry(period: PeriodEntity)

    @Query("SELECT * FROM period")
    fun getAllPeriodLogs(): Flow<List<PeriodEntity>>

    @Query("SELECT * FROM period WHERE id = :id")
    fun getPeriodLogById(id: Int): Flow<PeriodEntity>

    @Query("SELECT * FROM period WHERE month = :specifiedMonth AND year = :specifiedYear ORDER BY day ASC")
    fun getPeriodLogsForMonth(specifiedMonth: Int, specifiedYear: Int): Flow<List<PeriodEntity>>
}
