@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.flora1.features.calendar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.modifier.shadow
import com.example.flora1.core.presentation.ui.observers.ObserveAsEvents
import com.example.flora1.core.presentation.ui.uikit.buttons.CircleCloseButton
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.domain.db.model.Period
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE

@Composable
fun CalendarRoot(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val periods by viewModel.periods.collectAsStateWithLifecycle()
    val temporarySelectedPeriodDates by viewModel.temporarySelectedPeriodDates.collectAsStateWithLifecycle()
    val isEditing by viewModel.isEditing.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            CalendarEvent.NavigateBack -> onNavigateBack()
        }
    }

    CalendarRoot(
        periodDates = periods,
        onCloseClicked = viewModel::close,
        temporarySelectedPeriodDates = temporarySelectedPeriodDates,
        isEditing = isEditing,
        onEditClicked = viewModel::edit,
        onCancelClicked = viewModel::cancel,
        onTemporaryPeriodDateClicked = viewModel::onTemporaryPeriodSelected,
        onSaveClicked = viewModel::save,
    )
}


private val primaryColor = Color.Black.copy(alpha = 0.9f)
private val selectionColor = primaryColor
private val continuousSelectionColor = Color.LightGray.copy(alpha = 0.3f)

@Composable
fun CalendarRoot(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
    periodDates: Set<Period> = emptySet(),
    temporarySelectedPeriodDates: Set<Period> = emptySet(),
    isEditing: Boolean = false,
    onTemporaryPeriodDateClicked: (Period) -> Unit = {},
) {
    val currentMonth = remember { YearMonth.now() }
    val horizontalPadding = remember { 7.dp }
    val startMonth = remember { YearMonth.of(2022, 12) }
    val scope = rememberCoroutineScope()
    val endMonth = remember { currentMonth.plusYears(2) }
    val daysOfWeek = remember { daysOfWeek() }
    val today = remember { LocalDate.now() }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            val state = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = daysOfWeek.first(),
            )
            val shouldShowTodayText = remember {
                derivedStateOf {
                    !currentMonth.equals(state.firstVisibleMonth.yearMonth)
                }
            }

            TopCalendarBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(
                        horizontal = horizontalPadding,
                    )
                    .statusBarsPadding(),
                daysOfWeek = daysOfWeek,
                onCloseClick = onCloseClicked,
                todayClicked = {
                    scope.launch {
                        state.animateScrollToMonth(currentMonth)
                    }
                },
                shouldShowTodayText = shouldShowTodayText.value,
            )
            VerticalCalendar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(horizontal = horizontalPadding)
                    .animateContentSize(),
                state = state,
                contentPadding = PaddingValues(bottom = 100.dp),
                dayContent = { value ->
                    if (value.position == DayPosition.MonthDate)
                        if (!isEditing)
                            NonEditingDay(
                                value,
                                isSaved = value.date in periodDates.mapTo(HashSet<LocalDate>()) {
                                    it.date
                                },
                                enabled = value.date <= today,
                            )
                        else {
                            EditingDay(
                                day = value,
                                isSaved = value.date in temporarySelectedPeriodDates.mapTo(HashSet<LocalDate>()) {
                                    it.date
                                },
                                enabled = value.date <= today,
                                onClick = {
                                    onTemporaryPeriodDateClicked(Period(date = it.date))
                                }
                            )
                        }
                },
                monthContainer = { monthDisplayed, currentCalendarContent ->
                    Column(
                        modifier = Modifier.verticalCalendarModifier()
                    ) {
                        currentCalendarContent()
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceTint)
                            .height(5.dp)
                    )

                },

                monthHeader = { month ->
                    MonthHeader(month)
                },
            )
        }
        if (!isEditing) {
            EditPeriodButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter),
                onEditClicked = onEditClicked,
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .align(Alignment.BottomCenter)

                    .navigationBarsPadding(),
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding + horizontalPadding)
                        .padding(vertical = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 50))
                            .clickable(onClick = onCancelClicked)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = "Cancel",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 50))
                            .clickable(onClick = {
                                onSaveClicked()
                            })
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = "Save",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}

private val MONTH_CORNERS = 15.dp
fun Modifier.verticalCalendarModifier() = composed {
    val surfaceTint = MaterialTheme.colorScheme.surfaceTint
    val background = MaterialTheme.colorScheme.background
    return@composed remember {
        this
            .background(surfaceTint)

            .clip(
                shape = RoundedCornerShape(
                    MONTH_CORNERS
                )
            )
            .background(background)
    }
}

data class DateSelection(val startDate: LocalDate? = null, val endDate: LocalDate? = null) {
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) {
            null
        } else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }
}


@Composable
private fun NonEditingDay(
    day: CalendarDay,
    isSaved: Boolean = true,
    enabled: Boolean,
    onClick: (CalendarDay) -> Unit = {},
) {
    val textColor = when {
        isSaved -> MaterialTheme.colorScheme.onPrimary
        !enabled -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(10.dp)
            .clip(CircleShape)
            .background(if (isSaved) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable(
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun EditingDay(
    day: CalendarDay,
    isSaved: Boolean = true,
    onClick: (CalendarDay) -> Unit = {},
    enabled: Boolean,
) {
    val textColor = when {
        isSaved -> MaterialTheme.colorScheme.primary
        !enabled -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onBackground
    }

    val tickBorderColor = when {
        isSaved -> Color.Transparent
        !enabled -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onBackground
    }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(
            modifier = Modifier
                .aspectRatio(1f) // This is important for square-sizing!
                .padding(4.dp)
                .clip(CircleShape)
                .clickable(
                    enabled = enabled,
                    onClick = { onClick(day) },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = textColor,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(
                            width = 1.dp,
                            color = tickBorderColor,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .background(
                            if (isSaved)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Transparent
                        )
                        .padding(3.dp)
                ) {
                    if (isSaved)
                        Icon(
                            tint = MaterialTheme.colorScheme.background,
                            imageVector = Icons.Default.Check,
                            contentDescription = ""
                        )
                }
            }

        }
    }

}

@Composable
private fun MonthHeader(calendarMonth: CalendarMonth) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        textAlign = TextAlign.Center,
        text = calendarMonth.yearMonth.displayText(),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun TopCalendarBar(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek>,
    shouldShowTodayText: Boolean,
    onCloseClick: () -> Unit,
    todayClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircleCloseButton(
                onClick = onCloseClick,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (shouldShowTodayText)
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                        .clickable(onClick = todayClicked)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Today",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = dayOfWeek.displayText(),
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
private fun EditPeriodButton(
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit,
) {
    PrimaryButton(
        modifier =
        modifier
            .navigationBarsPadding(),
        shouldFillMaxWidth = false,
        paddingValues = PaddingValues(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        text = "Edit Period",
        onClick = onEditClicked,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
private fun CalendarBottomDef(
    modifier: Modifier = Modifier,
    selection: DateSelection,
    save: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "€75 night",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp),
                onClick = save,
                enabled = selection.daysBetween != null,
            ) {
                Text(text = "Save")
            }
        }
    }
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = true): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0F, 0.0F, 0.0f, 0.0F)
}

@PreviewLightDark
@Composable
private fun NonEditingPreview() {
    val now = remember {
        LocalDate.now()
    }
    val dates = List(10) {
        Period(
            date = LocalDate.of(
                now.year, now.month, it + 10
            )
        )

    }

    Flora1Theme {
        CalendarRoot(
            periodDates = dates.toSet(),
            isEditing = false,
        )
    }
}

@PreviewLightDark
@Composable
private fun EditingPreview() {
    val now = remember {
        LocalDate.now()
    }
    val dates = List(10) {
        Period(
            date = LocalDate.of(
                now.year, now.month, it + 10
            )
        )

    }

    Flora1Theme {
        CalendarRoot(
            temporarySelectedPeriodDates = dates.toSet(),
            isEditing = true,
        )
    }
}

