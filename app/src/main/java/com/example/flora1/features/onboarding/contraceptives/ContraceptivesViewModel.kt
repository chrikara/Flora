package com.example.flora1.features.onboarding.gynosurgery

import androidx.lifecycle.ViewModel
import com.example.flora1.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContraceptivesViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private var _selectedContraceptiveMethods : MutableStateFlow<List<ContraceptiveMethod>> =
        MutableStateFlow(emptyList())
    val selectedContraceptiveMethods : StateFlow<List<ContraceptiveMethod>> = _selectedContraceptiveMethods



    fun onSelectedContraceptiveMethodsChanged(contraceptiveMethods : List<ContraceptiveMethod>) {
        _selectedContraceptiveMethods.value = contraceptiveMethods
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
    OTHER("Other")
}

