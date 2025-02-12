package com.example.flora1.features.calendar

sealed interface CalendarEvent {
    data object NavigateBack : CalendarEvent
}
