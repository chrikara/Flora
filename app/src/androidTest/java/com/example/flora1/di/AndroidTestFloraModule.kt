package com.example.flora1.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.data.preferences.DefaultSharedPreferences
import com.example.flora1.data.preferences.USER_PREFERENCES
import com.example.flora1.domain.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FloraModule::class],
)
object AndroidTestFloraModule {
    @Provides
    @Singleton
    fun providesSharedPrefs(
        app : Application
    ) : SharedPreferences = app.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)


    @Provides
    @Singleton
    fun providesPreferences(
        sharedPrefs : SharedPreferences
    ) : Preferences = AndroidTestDefaultSharedPreferences(DefaultSharedPreferences(sharedPrefs))

    @Provides
    @Singleton
    fun providesDatabase(app: Application) : PeriodDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            PeriodDatabase::class.java,
            "food-database"
        ).build()
}



class AndroidTestDefaultSharedPreferences(preferences: Preferences) : Preferences by  preferences {
    override val shouldShowPredictionDialog: Boolean
        get() = true
}
