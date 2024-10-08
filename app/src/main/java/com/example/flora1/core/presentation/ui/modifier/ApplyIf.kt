package com.example.flora1.core.presentation.ui.modifier

import androidx.compose.ui.Modifier


fun Modifier.applyIf(
    enabled : Boolean,
    modifier : Modifier,
) : Modifier {
    return if (enabled) {
        then(modifier)
    } else {
        this
    }
}
