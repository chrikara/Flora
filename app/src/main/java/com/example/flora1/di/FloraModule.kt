package com.example.flora1.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.flora1.consent.data.ConsentApiGetDoctorsConsentUseCase
import com.example.flora1.consent.data.ConsentApiGrantConsentUseCase
import com.example.flora1.consent.data.ConsentApiHasGivenConsentUseCase
import com.example.flora1.consent.data.DefaultGetOwnersRequestsService
import com.example.flora1.consent.data.DefaultGetOwnersService
import com.example.flora1.consent.data.DefaultGrantConsentService
import com.example.flora1.consent.data.DefaultOwnersSignService
import com.example.flora1.consent.data.DefaultRevokeConsentService
import com.example.flora1.consent.data.GetOwnersRequestsService
import com.example.flora1.consent.data.GetOwnersService
import com.example.flora1.consent.data.GrantConsentService
import com.example.flora1.consent.data.OwnersSignService
import com.example.flora1.consent.data.RevokeConsentService
import com.example.flora1.consent.data.model.ConsentApiRevokeConsentUseCase
import com.example.flora1.consent.domain.GetDoctorsUseCase
import com.example.flora1.consent.domain.GrantConsentUseCase
import com.example.flora1.consent.domain.HasGivenConsentUseCase
import com.example.flora1.consent.domain.RevokeConsentUseCase
import com.example.flora1.core.network.clients.FLWebSocketClient
import com.example.flora1.core.network.clients.WebSocketClient
import com.example.flora1.data.auth.DefaultLoginService
import com.example.flora1.data.auth.DefaultRefreshService
import com.example.flora1.data.auth.DefaultRegisterService
import com.example.flora1.data.auth.DefaultUploadFloatsService
import com.example.flora1.data.auth.LoginService
import com.example.flora1.data.auth.RefreshService
import com.example.flora1.data.auth.RegisterService
import com.example.flora1.data.auth.UploadFloatsService
import com.example.flora1.data.db.PeriodDatabase
import com.example.flora1.domain.FloraDataStorePreferences
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.db.DeletePeriodUseCase
import com.example.flora1.domain.db.GetAllPeriodsUseCase
import com.example.flora1.domain.db.GetPeriodsForMonthUseCase
import com.example.flora1.domain.db.SavePeriodUseCase
import com.example.flora1.domain.personaldetails.DecimalConverter
import com.example.flora1.domain.personaldetails.DefaultDecimalConverter
import com.example.flora1.domain.personaldetails.DefaultHeightValidator
import com.example.flora1.domain.personaldetails.DefaultWeightValidator
import com.example.flora1.domain.personaldetails.HeightValidator
import com.example.flora1.domain.personaldetails.WeightValidator
import com.example.flora1.features.main.EthereumWrapper
import com.example.flora1.features.main.SomeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.metamask.androidsdk.DappMetadata
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.SDKOptions
import javax.inject.Singleton

private val Context.userDataStore by preferencesDataStore(
    name = "new_preferences"
)

@Module
@InstallIn(SingletonComponent::class)
object FloraModule {
    @Provides
    @Singleton
    fun providesDataStore(
        app: Application
    ): DataStore<androidx.datastore.preferences.core.Preferences> = app.userDataStore

    @Provides
    @Singleton
    fun providesDatabase(app: Application): PeriodDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            PeriodDatabase::class.java,
            "food-database"
        ).build()

    @Provides
    @Singleton
    fun providesRegisterService(): RegisterService =
        DefaultRegisterService()

    @Provides
    @Singleton
    fun providesLoginService(): LoginService =
        DefaultLoginService()

    @Provides
    @Singleton
    fun providesRefreshService(
        preferences: Preferences2,
    ): RefreshService =
        DefaultRefreshService(preferences = preferences)

    @Provides
    @Singleton
    fun providesUploadFloatsService(
        preferences: Preferences2,
    ): UploadFloatsService =
        DefaultUploadFloatsService(preferences = preferences)

    @Provides
    @Singleton
    fun providesWebSocketClient(
        preferences: Preferences2
    ): WebSocketClient =
        FLWebSocketClient(preferences = preferences)


    @Provides
    @Singleton
    fun providesGetPeriodsForMonthUseCase(
        db: PeriodDatabase,
    ): GetAllPeriodsUseCase =
        GetAllPeriodsUseCase(db)

    @Provides
    @Singleton
    fun providesGetAllPeriodsUseCase(
        db: PeriodDatabase,
    ): GetPeriodsForMonthUseCase =
        GetPeriodsForMonthUseCase(db)

    @Provides
    @Singleton
    fun providesSaveUseCase(
        db: PeriodDatabase,
    ): SavePeriodUseCase =
        SavePeriodUseCase(db)

    @Provides
    @Singleton
    fun providesDeleteUseCase(
        db: PeriodDatabase,
    ): DeletePeriodUseCase =
        DeletePeriodUseCase(db)

    @Provides
    @Singleton
    fun providesEthereumInstance(
        app: Application,
    ): Ethereum {
        val dappMetadata = DappMetadata("Droid Dapp", "https://www.droiddapp.io")
        val infuraAPIKey = "92a393fc2d4541e58dabc785f2e4d4f4"
        return Ethereum(app.applicationContext, dappMetadata, SDKOptions(infuraAPIKey, null))
    }

    @Provides
    @Singleton
    fun providesHeightValidator(): HeightValidator =
        DefaultHeightValidator()

    @Provides
    @Singleton
    fun providesWeightValidator(): WeightValidator =
        DefaultWeightValidator()

    @Provides
    @Singleton
    fun providesDecimalConverter(): DecimalConverter =
        DefaultDecimalConverter()

    @Provides
    @Singleton
    fun providesPreferences2(
        dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
        decimalConverter: DecimalConverter,
    ): Preferences2 = FloraDataStorePreferences(
        dataStore = dataStore,
        decimalConverter = decimalConverter,
    )

    @Provides
    @Singleton
    fun providesEthereumWrapper(
        ethereum: Ethereum,
    ): EthereumWrapper =
        SomeModel(
            ethereum = ethereum,
        )

    @Provides
    @Singleton
    fun providesGetOwnerService(): GetOwnersService = DefaultGetOwnersService()


    @Provides
    @Singleton
    fun providesHasGivenConsentUseCase(
        service: GetOwnersService,
        preferences: Preferences2,
        ethereum: Ethereum,
    ): HasGivenConsentUseCase = ConsentApiHasGivenConsentUseCase(
        getOwnersService = service,
        preferences = preferences,
        ethereum = ethereum,
    )

    @Provides
    @Singleton
    fun providesGetOwnersResponsesService(): GetOwnersRequestsService =
        DefaultGetOwnersRequestsService()


    @Provides
    @Singleton
    fun providesConsentApiGetDoctorsConsentUseCase(
        service: GetOwnersRequestsService,
    ): GetDoctorsUseCase =
        ConsentApiGetDoctorsConsentUseCase(
            getOwnersRequestsService = service,
        )

    @Provides
    @Singleton
    fun providesGrantConsentService(): GrantConsentService =
        DefaultGrantConsentService()

    @Provides
    @Singleton
    fun providesRevokeConsentService(): RevokeConsentService =
        DefaultRevokeConsentService()


    @Provides
    @Singleton
    fun providesGrantConsentUseCase(
        service: GrantConsentService,
    ): GrantConsentUseCase =
        ConsentApiGrantConsentUseCase(service)

    @Provides
    @Singleton
    fun providesRevokeConsentUseCase(
        service: RevokeConsentService,
    ): RevokeConsentUseCase =
        ConsentApiRevokeConsentUseCase(service)


    @Provides
    @Singleton
    fun providesOwnerSign(): OwnersSignService =
        DefaultOwnersSignService()


}
