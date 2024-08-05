package com.example.flora1.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.preferences.DefaultSharedPreferences
import com.example.flora1.domain.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FloraModule {

    @Provides
    @Singleton
    fun providesSharedPrefs(
        app : Application
    ) : SharedPreferences = app.getSharedPreferences("FloraPreferences", MODE_PRIVATE)


    @Provides
    @Singleton
    fun providesPreferences(
        sharedPrefs : SharedPreferences
    ) : Preferences = DefaultSharedPreferences(sharedPrefs)

    @Provides
    @Singleton
    fun providesDatabase(app:Application) : PeriodDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            PeriodDatabase::class.java,
            "food-database"
        ).build()

}
