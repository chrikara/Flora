package com.example.flora1.core.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

private tailrec fun Context.getActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

val Context.getActivity get() = getActivity()
