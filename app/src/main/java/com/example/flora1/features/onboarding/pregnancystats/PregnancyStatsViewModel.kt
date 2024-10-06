package com.example.flora1.features.onboarding.weight

import androidx.lifecycle.ViewModel
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PregnancyStatsViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private var _pregnancies: MutableStateFlow<NumericalOptions?> = MutableStateFlow(null)
    val pregnancies: StateFlow<NumericalOptions?> = _pregnancies

    private var _miscarriages: MutableStateFlow<NumericalOptions?> = MutableStateFlow(null)
    val miscarriages: StateFlow<NumericalOptions?> = _miscarriages

    private var _abortions: MutableStateFlow<NumericalOptions?> = MutableStateFlow(null)
    val abortions: StateFlow<NumericalOptions?> = _abortions

    private var _isBreastfeeding: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBreastfeeding: StateFlow<Boolean> = _isBreastfeeding

    fun onPregnanciesChanged(pregnancy: NumericalOptions?) {
        _pregnancies.value = pregnancy
    }

    fun onMiscarriagesChanged(miscarriage: NumericalOptions?) {
        _miscarriages.value = miscarriage
    }

    fun onAbortionsChanged(abortion: NumericalOptions?) {
        _abortions.value = abortion
    }

    fun onBreastfeedingChanged(isBreastfeeding: Boolean) {
        _isBreastfeeding.value = isBreastfeeding
    }

    fun onSaveTotalPregnancies(option : NumericalOptions) {
        preferences.saveTotalPregnancies(option)
    }

    fun onSaveTotalMiscarriages(option : NumericalOptions) {
        preferences.saveTotalMiscarriages(option)
    }

    fun onSaveTotalAbortions(option : NumericalOptions) {
        preferences.saveTotalAbortions(option)
    }

    companion object {
        const val MAX_WEIGHT_CHARS = 5
        const val DEFAULT_PREGNANT_VALUE = "Yes"
        const val DEFAULT_NOT_PREGNANT_VALUE = "No"
        const val DEFAULT_NO_COMMENT_VALUE = "No comment"
    }
}

enum class NumericalOptions(val text: String) {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    MORE_THAN_SIX("6+");

    fun toIntOrNull() : Int? = text.replace('+', ' ').toIntOrNull()

    companion object {
        fun fromString(text : String) =
            when(text){
                "0" -> ZERO
                "1" -> ZERO
                "2" -> ZERO
                "3" -> ZERO
                "4" -> ZERO
                "5" -> ZERO
                "6+" -> ZERO
                else -> null
        }
    }
}

