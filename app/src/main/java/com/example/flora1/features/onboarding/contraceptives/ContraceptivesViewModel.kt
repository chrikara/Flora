package com.example.flora1.features.onboarding.contraceptives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.domain.Preferences2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContraceptivesViewModel @Inject constructor(
    private val preferences: Preferences2,
) : ViewModel() {

    private var _selectedContraceptiveMethods: MutableStateFlow<List<ContraceptiveMethod>> =
        MutableStateFlow(emptyList())
    val selectedContraceptiveMethods: StateFlow<List<ContraceptiveMethod>> =
        _selectedContraceptiveMethods


    fun onSelectedContraceptiveMethodsChanged(contraceptiveMethods: List<ContraceptiveMethod>) {
        _selectedContraceptiveMethods.value = contraceptiveMethods
    }

    fun onSaveContraceptiveMethods(contraceptiveMethods: List<ContraceptiveMethod>) {
        viewModelScope.launch {
            preferences.saveContraceptiveMethods(contraceptiveMethods)
        }
    }
}

enum class ContraceptiveMethod(val text: String) {
    PILL("Pill"),
    IUD("IUD"),
    INJECTION("Injection"),
    BARRIER("Barrier (condoms, diaphragms, etc.)"),
    NORPLANT("Norplant"),
    WITHDRAWAL("Withdrawal"),
    ABSTINENCE("Abstinence"),
    NEP("NEP"),
    OTHER("Other");

    companion object {
        fun fromString(text: String): ContraceptiveMethod = when (text) {
            PILL.text -> PILL
            IUD.text -> IUD
            INJECTION.text -> INJECTION
            BARRIER.text -> BARRIER
            NORPLANT.text -> NORPLANT
            WITHDRAWAL.text -> WITHDRAWAL
            ABSTINENCE.text -> ABSTINENCE
            NEP.text -> NEP
            OTHER.text -> OTHER
            else -> PILL
        }
    }
}

