package com.example.flora1.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PeriodEntity (
    @PrimaryKey val id :Int? = null,
    val flow : String,
    val day :Int,
    val month: Int,
    val year : Int,
)
