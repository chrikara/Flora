package com.example.flora1.data.preferences

import android.content.SharedPreferences
import com.example.flora1.domain.Preferences
import com.example.flora1.features.onboarding.contraceptives.ContraceptiveMethod
import com.example.flora1.features.onboarding.race.Race
import com.example.flora1.features.onboarding.stresstilllastperiod.StressLevelTillLastPeriod
import com.example.flora1.features.onboarding.weight.NumericalOptions
import com.example.flora1.features.onboarding.weight.PregnancyStatus

class DefaultSharedPreferences(
    private val sharedPref: SharedPreferences

) : Preferences {
    override fun saveAverageCycle(averageCycleDays: Int) {
        sharedPref.edit().putInt(KEY_AVERAGE_CYCLE, averageCycleDays).apply()
    }

    override fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean) {
        sharedPref.edit().putBoolean(KEY_SHOULD_SHOW_ONBOARDING, shouldShowOnBoarding).apply()
    }

    override fun saveDateOfBirth(dateOfBirth: Long) {
        sharedPref.edit().putLong(KEY_DATE_OF_BIRTH, dateOfBirth).apply()
    }

    override fun saveUsername(username: String) {
        sharedPref.edit().putString(KEY_USERNAME, username).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit().putFloat(KEY_WEIGHT, weight).apply()
    }

    override fun savePregnancyStatus(pregnancyStatus: PregnancyStatus) {
        sharedPref.edit().putString(KEY_PREGNANCY_STATUS, pregnancyStatus.value).apply()
    }

    override fun saveTotalPregnancies(number: NumericalOptions) {
        sharedPref.edit().putString(KEY_TOTAL_PREGNANCIES, number.text).apply()
    }

    override fun saveTotalMiscarriages(number: NumericalOptions) {
        sharedPref.edit().putString(KEY_TOTAL_MISCARRIAGES, number.text).apply()
    }

    override fun saveHasTakenMedVits(hasTakenMedVits: Boolean) {
        sharedPref.edit().putBoolean(KEY_HAS_TAKEN_MEDVITS, hasTakenMedVits).apply()
    }

    override fun saveMedVitsDescription(description: String) {
        sharedPref.edit().putString(KEY_MEDVITS_DESCRIPTION, description).apply()
    }

    override fun saveHasDoneGynecosurgery(hasDoneGynecosurgery: Boolean) {
        sharedPref.edit().putBoolean(KEY_HAS_DONE_GYNECOSURGERY, hasDoneGynecosurgery).apply()
    }

    override fun saveGyncosurgeryDescription(description: String) {
        sharedPref.edit().putString(KEY_GYNECOSURGERY_DESCRIPTION, description).apply()
    }

    override fun saveIsBreastfeeding(isBreastfeeding: Boolean) {
        sharedPref.edit().putBoolean(KEY_IS_BREASTFEEDING, isBreastfeeding).apply()
    }

    override fun saveRace(race: Race) {
        sharedPref.edit().putString(KEY_RACE, race.text).apply()
    }

    override val race: Race
        get() = Race.fromString(sharedPref.getString(KEY_RACE, "") ?: "")

    override fun saveTotalAbortions(number: NumericalOptions) {
        sharedPref.edit().putString(KEY_TOTAL_ABORTIONS, number.text).apply()
    }

    override fun saveContraceptiveMethods(contraceptiveMethods: List<ContraceptiveMethod>) {
        contraceptiveMethods.forEachIndexed { index, contraceptiveMethod ->
            sharedPref.edit()
                .putString(getKeyOfContraceptiveMethod(index), contraceptiveMethod.text).apply()
        }
    }

    override fun saveHeight(height: Float) {
        sharedPref.edit().putFloat(KEY_HEIGHT, height).apply()
    }


    override val height: Float get() = sharedPref.getFloat(KEY_HEIGHT, 0f)
    override val username: String get() = sharedPref.getString(KEY_USERNAME, "") ?: ""
    override val weight: Float get() = sharedPref.getFloat(KEY_WEIGHT, 0f)
    override val pregnancyStatus: PregnancyStatus
        get() =
            PregnancyStatus.fromString(sharedPref.getString(KEY_PREGNANCY_STATUS, "") ?: "")
    override val totalPregnancies: NumericalOptions?
        get() = NumericalOptions.fromString(sharedPref.getString(KEY_TOTAL_PREGNANCIES, "") ?: "")
    override val totalMiscarriages: NumericalOptions?
        get() = NumericalOptions.fromString(sharedPref.getString(KEY_TOTAL_MISCARRIAGES, "") ?: "")
    override val totalAbortions: NumericalOptions?
        get() = NumericalOptions.fromString(sharedPref.getString(KEY_TOTAL_ABORTIONS, "") ?: "")
    override val averageCycleDays get() = sharedPref.getInt(KEY_AVERAGE_CYCLE, 0)

    override val contraceptiveMethods: List<ContraceptiveMethod>
        get() = buildList {
            repeat(ContraceptiveMethod.entries.size) {
                val contraceptiveMethodText =
                    sharedPref.getString(getKeyOfContraceptiveMethod(it), "") ?: ""

                if (contraceptiveMethodText.isBlank())
                    return@buildList
                else
                    add(ContraceptiveMethod.fromString(contraceptiveMethodText))
            }
        }
    override val hasDoneGynecosurgery: Boolean
        get() = sharedPref.getBoolean(KEY_HAS_DONE_GYNECOSURGERY, false)
    override val gyncosurgeryDescription: String
        get() = sharedPref.getString(KEY_GYNECOSURGERY_DESCRIPTION, "") ?: ""

    override fun saveStressLevelTillLastPeriod(stressLevel: StressLevelTillLastPeriod) {
        sharedPref.edit().putString(KEY_STRESS_LEVEL_TILL_LAST_PERIOD, stressLevel.text).apply()
    }

    override val stressLevelTillLastPeriod: StressLevelTillLastPeriod
        get() = StressLevelTillLastPeriod.fromString(
            sharedPref.getString(
                KEY_STRESS_LEVEL_TILL_LAST_PERIOD,
                ""
            ) ?: ""
        )
    override val hasTakenMedVits: Boolean
        get() = sharedPref.getBoolean(KEY_HAS_TAKEN_MEDVITS, false)
    override val medVitsDescription: String
        get() = sharedPref.getString(KEY_MEDVITS_DESCRIPTION, "") ?: ""


    override val isBreastfeeding: Boolean
        get() = sharedPref.getBoolean(KEY_IS_BREASTFEEDING, false)
    override val shouldShowOnBoarding
        get() = sharedPref.getBoolean(
            KEY_SHOULD_SHOW_ONBOARDING,
            true
        )
    override val dateOfBirth get() = sharedPref.getString(KEY_DATE_OF_BIRTH, "") ?: ""

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_HEIGHT = "height"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_PREGNANCY_STATUS = "pregnancyStatus"
        private const val KEY_TOTAL_PREGNANCIES = "totalPregnancies"
        private const val KEY_TOTAL_MISCARRIAGES = "totalMiscarriages"
        private const val KEY_TOTAL_ABORTIONS = "totalAbortions"
        private const val KEY_RACE = "race"
        private const val KEY_HAS_TAKEN_MEDVITS = "hasTakenMedVits"
        private const val KEY_MEDVITS_DESCRIPTION = "medVitsDescription"
        private const val KEY_HAS_DONE_GYNECOSURGERY = "hasDoneGynecosurgery"
        private const val KEY_GYNECOSURGERY_DESCRIPTION = "gynecosurgeryDescription"
        private const val KEY_IS_BREASTFEEDING = "isBreastfeeding"
        private const val KEY_STRESS_LEVEL_TILL_LAST_PERIOD = "stressLevelTillLastPeriod"
        private const val KEY_AVERAGE_CYCLE = "averageCycleDays"
        private const val KEY_SHOULD_SHOW_ONBOARDING = "shouldShowOnBoarding"
        private const val KEY_DATE_OF_BIRTH = "dateOfBirth"

        private fun getKeyOfContraceptiveMethod(number: Int) = "contraceptiveMethod$number"
    }
}
