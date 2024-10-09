package com.example.flora1.core.presentation.ui.uikit.bottomsheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> ChoiceComboBoxBottomSheet(
    modifier: Modifier = Modifier,
    options: List<T>,
    optionText: (T) -> String,
    isChecked: (T) -> Boolean,
    title: String,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onOptionClicked: (T) -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        title = title,
        modalSheetState = sheetState,
        onDismissRequest = onDismissRequest,
        content = {
            options.forEach { option ->
                Row(
                    modifier = Modifier.clickable(onClick = {
                        onOptionClicked(option)
                    }),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DefaultChoiceRowContent(
                        option = option,
                        optionText = optionText(option),
                        isChecked = isChecked(option),
                        onCheckboxChanged = {
                            onOptionClicked(option)
                        },
                    )
                }
            }
        }
    )
}

@Composable
private fun <T> RowScope.DefaultChoiceRowContent(
    option: T,
    optionImage: ((T) -> Int)? = null,
    optionText: String,
    isChecked: Boolean,
    onCheckboxChanged: (Boolean) -> Unit,
) {
    optionImage?.let {
        Image(
            modifier = Modifier
                .size(28.dp),
            painter = painterResource(id = it(option)),
            contentDescription = null,
        )
    }
    Text(
        modifier = Modifier
            .weight(1f)
            .padding(
                top = 9.dp,
                bottom = 9.dp,
                end = 22.dp,
            ),
        text = optionText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium,
    )

    Checkbox(
        checked = isChecked,
        onCheckedChange = onCheckboxChanged,
    )
}







