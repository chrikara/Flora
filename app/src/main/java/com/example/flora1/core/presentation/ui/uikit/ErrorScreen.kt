package com.example.flora1.core.presentation.ui.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton

@Composable
internal fun ErrorScreen(
    onRetryButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = stringResource(R.string.something_went_wrong))

        Spacer(modifier = Modifier.height(30.dp))

        PrimaryButton(
            shouldFillMaxWidth = false,
            text = stringResource(R.string.retry),
            onClick = onRetryButtonClicked,
        )
    }
}
