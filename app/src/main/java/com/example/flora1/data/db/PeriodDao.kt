package com.example.flora1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDao {


    @Upsert
    suspend fun savePeriodEntry(food : PeriodEntity)

    @Delete
    suspend fun deletePeriodEntry(food : PeriodEntity)

//    @Query("""
//
//        SELECT * FROM foodentity WHERE day = :day AND month= :month AND year = :year AND mealName = :mealName
//
//    """
//    )
//    fun getFoodsForDateAndMealType(day : Int, month : Int, year: Int, mealName : String) : Flow<List<FoodEntity>>
//
//
//    @Query("""
//
//        SELECT * FROM foodentity WHERE day = :day AND month= :month AND year = :year
//
//    """
//    )
//    fun getFoodsForDate(day : Int, month : Int, year: Int) : Flow<List<FoodEntity>>

}
