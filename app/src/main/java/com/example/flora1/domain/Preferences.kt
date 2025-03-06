package com.example.flora1.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.flora1.domain.personaldetails.DecimalConverter
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.sleepqualitytilllastperiod.SleepQuality
import com.example.flora1.features.onboarding.stresstilllastperiod.StressLevelTillLastPeriod
import com.example.flora1.features.onboarding.weight.NumericalOptions
import com.example.flora1.features.onboarding.weight.PregnancyStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface Preferences2 {
    suspend fun saveDateOfBirth(dateOfBirth: Long)
    val dateOfBirth: Flow<Long>

    suspend fun saveToken(token: String)
    val token: Flow<String>
    suspend fun logout()

    suspend fun saveHasGivenDataConsent(hasGivenDataConsent: Boolean)
    val hasGivenDataConsent: Flow<Boolean?>

    suspend fun updateTheme(theme: Theme)
    val theme: Flow<Theme>

    suspend fun saveWeight(weight: Float)
    val weight: Flow<String>

    suspend fun saveHeight(height: Float)
    val height: Flow<String>

    suspend fun savePregnancyStatus(pregnancyStatus: PregnancyStatus)
    val pregnancyStatus: Flow<PregnancyStatus>

    suspend fun saveTotalPregnancies(number: NumericalOptions)
    val totalPregnancies: Flow<NumericalOptions?>

    suspend fun saveTotalMiscarriages(number: NumericalOptions)
    val totalMiscarriages: Flow<NumericalOptions?>

    suspend fun saveTotalAbortions(number: NumericalOptions)
    val totalAbortions: Flow<NumericalOptions?>

    suspend fun saveAverageCycle(averageCycleDays: Int)
    val averageCycleDays: Flow<Int>

    suspend fun saveRace(race: Race)
    val race: Flow<Race>

    suspend fun saveHasTakenMedVits(hasTakenMedVits: Boolean)
    val hasTakenMedVits: Flow<Boolean>

    suspend fun saveMedVitsDescription(description: String)
    val medVitsDescription: Flow<String>

    suspend fun saveHasDoneGynecosurgery(hasDoneGynecosurgery: Boolean)
    val hasDoneGynecosurgery: Flow<Boolean>

    suspend fun saveGyncosurgeryDescription(description: String)
    val gyncosurgeryDescription: Flow<String>

    suspend fun saveIsBreastfeeding(isBreastfeeding: Boolean)
    val isBreastfeeding: Flow<Boolean>

    suspend fun saveShouldShowPredictionDialog(shouldShowPredictionDialog: Boolean)
    val shouldShowPredictionDialog: Flow<Boolean>

    suspend fun saveIsPredictionModeEnabled(isPredictionModeEnabled: Boolean)
    val isPredictionModeEnabled: Flow<Boolean>

    suspend fun saveContraceptiveMethods(contraceptiveMethods: List<ContraceptiveMethod>)
    val contraceptiveMethods: Flow<List<ContraceptiveMethod>>

    suspend fun saveStressLevelTillLastPeriod(stressLevel: StressLevelTillLastPeriod)
    val stressLevelTillLastPeriod: Flow<StressLevelTillLastPeriod>

    suspend fun saveSleepQualityTillLastPeriod(sleepQuality: SleepQuality)
    val sleepQualityTillLastPeriod: Flow<SleepQuality>

    suspend fun saveMetamaskSignature(metamaskSignature: String)
    val metamaskSignature: Flow<String>

    val isLoggedIn: Flow<Boolean>
}

class FloraDataStorePreferences(
    private val dataStore: DataStore<Preferences>,
    private val decimalConverter: DecimalConverter,
) : Preferences2 {
    override suspend fun saveDateOfBirth(dateOfBirth: Long) {
        dataStore.edit { it[DATE_OF_BIRTH] = dateOfBirth }
    }

    override val dateOfBirth: Flow<Long>
        get() = dataStore.data.flowWithCatch().map { it[DATE_OF_BIRTH] ?: 0L }

    override suspend fun saveTotalPregnancies(number: NumericalOptions) {
        dataStore.edit { preferences ->
            preferences[TOTAL_PREGNANCIES_KEY] = number.name
        }
    }

    override val totalPregnancies: Flow<NumericalOptions?> =
        dataStore.data.map { preferences ->
            preferences[TOTAL_PREGNANCIES_KEY]?.let { NumericalOptions.valueOf(it) }
        }

    override suspend fun saveTotalMiscarriages(number: NumericalOptions) {
        dataStore.edit { preferences ->
            preferences[TOTAL_MISCARRIAGES_KEY] = number.name
        }
    }

    override val totalMiscarriages: Flow<NumericalOptions?> =
        dataStore.data.map { preferences ->
            preferences[TOTAL_MISCARRIAGES_KEY]?.let { NumericalOptions.valueOf(it) }
        }

    override suspend fun saveTotalAbortions(number: NumericalOptions) {
        dataStore.edit { preferences ->
            preferences[TOTAL_ABORTIONS_KEY] = number.name
        }
    }

    override val totalAbortions: Flow<NumericalOptions?> =
        dataStore.data.map { preferences ->
            preferences[TOTAL_ABORTIONS_KEY]?.let { NumericalOptions.valueOf(it) }
        }

    override suspend fun saveRace(race: Race) {
        dataStore.edit { preferences ->
            preferences[RACE_KEY] = race.text
        }
    }

    override val race: Flow<Race> =
        dataStore.data.map { preferences ->
            Race.fromString(preferences[RACE_KEY] ?: "")
        }

    override suspend fun saveHasTakenMedVits(hasTakenMedVits: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_TAKEN_MED_VITS_KEY] = hasTakenMedVits
        }
    }

    override val hasTakenMedVits: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_TAKEN_MED_VITS_KEY] ?: false
        }

    override suspend fun saveMedVitsDescription(description: String) {
        dataStore.edit { preferences ->
            preferences[MED_VITS_DESCRIPTION_KEY] = description
        }
    }

    override val medVitsDescription: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[MED_VITS_DESCRIPTION_KEY] ?: ""
        }

    override suspend fun saveHasDoneGynecosurgery(hasDoneGynecosurgery: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_DONE_GYNECOSURGERY_KEY] = hasDoneGynecosurgery
        }
    }

    override val hasDoneGynecosurgery: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_DONE_GYNECOSURGERY_KEY] ?: false
        }

    override suspend fun saveGyncosurgeryDescription(description: String) {
        dataStore.edit { preferences ->
            preferences[GYNECOSURGERY_DESCRIPTION_KEY] = description
        }
    }

    override val gyncosurgeryDescription: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[GYNECOSURGERY_DESCRIPTION_KEY] ?: ""
        }

    override suspend fun saveIsBreastfeeding(isBreastfeeding: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_BREASTFEEDING_KEY] = isBreastfeeding
        }
    }

    override val isBreastfeeding: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[IS_BREASTFEEDING_KEY] ?: false
        }

    override suspend fun saveShouldShowPredictionDialog(shouldShowPredictionDialog: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOULD_SHOW_PREDICTION_DIALOG_KEY] = shouldShowPredictionDialog
        }
    }

    override val shouldShowPredictionDialog: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[SHOULD_SHOW_PREDICTION_DIALOG_KEY] ?: true
        }

    override suspend fun saveIsPredictionModeEnabled(isPredictionModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOULD_SHOW_PREDICTIONS_KEY] = isPredictionModeEnabled
        }
    }

    override val isPredictionModeEnabled: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[SHOULD_SHOW_PREDICTIONS_KEY] ?: true
        }

    override suspend fun saveContraceptiveMethods(contraceptiveMethods: List<ContraceptiveMethod>) {
        dataStore.edit { preferences ->
            preferences[CONTRACEPTIVE_METHODS_KEY] =
                contraceptiveMethods.map(ContraceptiveMethod::text).toSet()
        }
    }

    override val contraceptiveMethods: Flow<List<ContraceptiveMethod>> =
        dataStore.data.map { preferences ->
            preferences[CONTRACEPTIVE_METHODS_KEY]
                ?.map(ContraceptiveMethod::fromString)
                ?: emptyList()
        }

    override suspend fun saveStressLevelTillLastPeriod(stressLevel: StressLevelTillLastPeriod) {
        dataStore.edit { preferences ->
            preferences[STRESS_LEVEL_TILL_LAST_PERIOD_KEY] = stressLevel.name
        }
    }

    override val stressLevelTillLastPeriod: Flow<StressLevelTillLastPeriod> =
        dataStore.data.map { preferences ->
            StressLevelTillLastPeriod.fromString(
                preferences[STRESS_LEVEL_TILL_LAST_PERIOD_KEY] ?: ""
            )

        }

    override suspend fun saveSleepQualityTillLastPeriod(sleepQuality: SleepQuality) {
        dataStore.edit { preferences ->
            preferences[SLEEP_QUALITY_TILL_LAST_PERIOD_KEY] = sleepQuality.name
        }
    }

    override val sleepQualityTillLastPeriod: Flow<SleepQuality> =
        dataStore.data.map { preferences ->
            SleepQuality.fromString(preferences[SLEEP_QUALITY_TILL_LAST_PERIOD_KEY] ?: "")
        }

    override suspend fun saveMetamaskSignature(metamaskSignature: String) {
        dataStore.edit { preferences ->
            preferences[SAVE_METAMASK_SIGNATURE_KEY] = metamaskSignature
        }
    }

    override val metamaskSignature: Flow<String> =
        dataStore.data.flowWithCatch().map { preferences ->
            preferences[SAVE_METAMASK_SIGNATURE_KEY] ?: ""
        }

    override val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[TOKEN]?.isNotEmpty() ?: false
        }

    override suspend fun saveToken(token: String) {
        dataStore.edit { it[TOKEN] = token }
    }

    override suspend fun logout() {
        dataStore.edit { it[TOKEN] = "" }
    }

    override val token: Flow<String>
        get() = dataStore.data.flowWithCatch().map { it[TOKEN] ?: "" }

    override suspend fun saveHasGivenDataConsent(hasGivenDataConsent: Boolean) {
        dataStore.edit { it[HAS_GIVEN_DATA_CONSENT] = hasGivenDataConsent }
    }

    override val hasGivenDataConsent: Flow<Boolean?>
        get() = dataStore.data.flowWithCatch().map { it[HAS_GIVEN_DATA_CONSENT] }

    override suspend fun updateTheme(theme: Theme) {
        dataStore.edit { it[IS_DARK] = theme.ordinal }
    }

    override val theme: Flow<Theme>
        get() = dataStore.data.flowWithCatch().map { Theme.entries[it[IS_DARK] ?: 0] }

    override suspend fun saveWeight(weight: Float) {
        dataStore.edit { it[WEIGHT] = weight }
    }

    override val weight: Flow<String>
        get() = dataStore.data.flowWithCatch().map {
            with(decimalConverter) {
                it[WEIGHT]?.convertDecimalToString() ?: ""
            }
        }

    override suspend fun saveHeight(height: Float) {
        dataStore.edit { it[HEIGHT] = height }
    }

    override val height: Flow<String>
        get() = dataStore.data.flowWithCatch().map {
            with(decimalConverter) {
                it[HEIGHT]?.convertDecimalToString() ?: ""
            }

        }

    override suspend fun savePregnancyStatus(pregnancyStatus: PregnancyStatus) {
        dataStore.edit { it[PREGNANCY_STATUS] = pregnancyStatus.value }
    }

    override val pregnancyStatus: Flow<PregnancyStatus>
        get() = dataStore.data.flowWithCatch()
            .map { PregnancyStatus.fromString(it[PREGNANCY_STATUS] ?: "") }

    override suspend fun saveAverageCycle(averageCycleDays: Int) {
        dataStore.edit { it[AVERAGE_CYCLE] = averageCycleDays }
    }

    override val averageCycleDays: Flow<Int>
        get() = dataStore.data.flowWithCatch().map { it[AVERAGE_CYCLE] ?: 0 }

    companion object {
        const val KEY_USERNAME = "token"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_PREGNANCY_STATUS = "pregnancyStatus"
        const val KEY_HAS_GIVEN_DATA_CONSENT = "hasGivenDataConsent"
        const val KEY_AVERAGE_CYCLE = "averageCycleDays"
        const val KEY_DATE_OF_BIRTH = "dateOfBirth"

        val DATE_OF_BIRTH = longPreferencesKey(KEY_DATE_OF_BIRTH)
        val TOKEN = stringPreferencesKey(KEY_USERNAME)
        val HAS_GIVEN_DATA_CONSENT = booleanPreferencesKey(KEY_HAS_GIVEN_DATA_CONSENT)
        val IS_DARK = intPreferencesKey("isDark")
        val WEIGHT = floatPreferencesKey(KEY_WEIGHT)
        val HEIGHT = floatPreferencesKey(KEY_HEIGHT)
        val PREGNANCY_STATUS = stringPreferencesKey(KEY_PREGNANCY_STATUS)
        val AVERAGE_CYCLE = intPreferencesKey(KEY_AVERAGE_CYCLE)
        private val TOTAL_PREGNANCIES_KEY = stringPreferencesKey("total_pregnancies")
        private val TOTAL_MISCARRIAGES_KEY = stringPreferencesKey("total_miscarriages")
        private val TOTAL_ABORTIONS_KEY = stringPreferencesKey("total_abortions")
        private val RACE_KEY = stringPreferencesKey("race")
        private val HAS_TAKEN_MED_VITS_KEY = booleanPreferencesKey("has_taken_med_vits")
        private val MED_VITS_DESCRIPTION_KEY = stringPreferencesKey("med_vits_description")
        private val HAS_DONE_GYNECOSURGERY_KEY = booleanPreferencesKey("has_done_gynecosurgery")
        private val GYNECOSURGERY_DESCRIPTION_KEY =
            stringPreferencesKey("gynecosurgery_description")
        private val IS_BREASTFEEDING_KEY = booleanPreferencesKey("is_breastfeeding")
        private val SHOULD_SHOW_PREDICTION_DIALOG_KEY =
            booleanPreferencesKey("should_show_prediction_dialog")
        private val SHOULD_SHOW_PREDICTIONS_KEY = booleanPreferencesKey("should_show_predictions")
        private val CONTRACEPTIVE_METHODS_KEY = stringSetPreferencesKey("contraceptive_methods")
        private val STRESS_LEVEL_TILL_LAST_PERIOD_KEY =
            stringPreferencesKey("stress_level_till_last_period")
        private val SLEEP_QUALITY_TILL_LAST_PERIOD_KEY =
            stringPreferencesKey("sleep_quality_till_last_period")

        private val SAVE_METAMASK_SIGNATURE_KEY =
            stringPreferencesKey("saveMetamaskSignature")


    }
}

private fun Flow<Preferences>.flowWithCatch(): Flow<Preferences> =
    catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
