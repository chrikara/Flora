@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.uikit.buttons.CircleCloseButton
import com.example.flora1.core.presentation.ui.uikit.buttons.CloseButton
import kotlinx.coroutines.launch

@Composable
fun CalendarBottomSheet(
    title : String = "",
    onDismiss : () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),

) {
    val scope = rememberCoroutineScope()
    val closeBottomSheet = {
        scope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {closeBottomSheet()},
        sheetState = sheetState,
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                )

                CloseButton(
                    onCloseButtonClicked = { closeBottomSheet() }
                )
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    Flora1Theme {

        CalendarBottomSheet(
            title = "Tracked on 11 Feb",
        )
    }
}
