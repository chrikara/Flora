package com.example.flora1.core.presentation.ui.errorhandling

import com.example.flora1.R
import com.example.flora1.core.domain.util.DataError
import com.example.flora1.core.presentation.ui.localization.UiText

// These should be reusable in whole project. If we have a specific
// string resource, like for height-weight toast messages, then these
// string and helper classes the belong to can reside only in the
// OnBoarding module.

fun DataError.asUiText(): UiText = when (this) {
    DataError.Local.DISK_FULL -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.UNAUTHORIZED -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.CONFLICT -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.NO_INTERNET -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.SERVER_ERROR -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.SERIALIZATION -> UiText.StringResource(
        id = R.string.app_name,
    )

    DataError.Network.UNKNOWN -> UiText.StringResource(
        id = R.string.app_name,
    )
}
