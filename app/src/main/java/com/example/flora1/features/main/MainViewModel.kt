package com.example.flora1.features.main

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flora1.core.network.clients.WebSocketClient
import com.example.flora1.data.auth.RefreshService
import com.example.flora1.data.auth.UploadFloatsService
import com.example.flora1.domain.Preferences2
import com.example.flora1.domain.db.GetPeriodsForMonthUseCase
import com.example.flora1.domain.db.SavePeriodUseCase
import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.abs


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPeriodsForMonthUseCase: GetPeriodsForMonthUseCase,
    private val savePeriodUseCase: SavePeriodUseCase,
    private val preferences: Preferences2,
    private val refreshService: RefreshService,
    private val uploadFloatsService: UploadFloatsService,
    private val webSocketClient: WebSocketClient,
) : ViewModel() {
    private val _selectedDay = MutableStateFlow(LocalDate.now().dayOfMonth)
    val selectedDay: StateFlow<Int> = _selectedDay


    private var _shouldShowPredictionDialog = MutableStateFlow(false)
    val shouldShowPredictionDialog: StateFlow<Boolean> = _shouldShowPredictionDialog

    init {
        viewModelScope.launch {
            _shouldShowPredictionDialog.update {
                preferences.shouldShowPredictionDialog.firstOrNull() ?: false
            }
        }
    }

    fun onAccept() {
        viewModelScope.launch {
            _shouldShowPredictionDialog.update { false }
            preferences.saveShouldShowPredictionDialog(false)
            preferences.saveIsPredictionModeEnabled(true)
        }
    }

    fun onDismiss() {
        viewModelScope.launch {
            _shouldShowPredictionDialog.update { false }
            preferences.saveShouldShowPredictionDialog(false)
        }
    }

    val periodDaysForCurrentMonth = run {
        val localDate = LocalDate.now()
        getPeriodsForMonthUseCase.getPeriodsForMonth(localDate.monthValue, localDate.year)
    }
        .map { fetchedCurrentMonthPeriodLogs ->
            fetchedCurrentMonthPeriodLogs.map {
                it.date.dayOfMonth
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )


    val primaryText = combine(
        selectedDay, periodDaysForCurrentMonth,
    ) { selectedDay, periodDaysForCurrentMonth ->
        if (selectedDay in periodDaysForCurrentMonth) {
            if (!periodDaysForCurrentMonth.contains(selectedDay + 1))
                "Last Day"
            else {
                var daysLeft = 1

                while (periodDaysForCurrentMonth.contains(selectedDay + daysLeft)) {
                    daysLeft++
                }
                "$daysLeft days remaining"
            }
        } else
            "No period today"

    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )


    val selectedDate: StateFlow<LocalDate> = selectedDay.mapLatest { day ->
        val localDate = LocalDate.now()
        LocalDate.of(
            localDate.year,
            localDate.monthValue,
            day,
        )
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = LocalDate.now()
        )


    fun onArcClicked(
        offsetClicked: Offset,
        circlePositions: List<Pair<Float, Float>>,
        circleRadius: Float,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                circlePositions.forEachIndexed { index, position ->
                    if (abs(offsetClicked.x - position.first) < circleRadius * 2f
                        && abs(offsetClicked.y - position.second) < circleRadius * 2f
                    ) {
                        _selectedDay.update { index + 1 }
                        return@forEachIndexed
                    }
                }
            }
        }
    }

    private val _isConnectedToSocket = MutableStateFlow(false)
    val isConnectedToSocket: StateFlow<Boolean> = _isConnectedToSocket

    val latestReceivedMessage =
        _isConnectedToSocket
            .flatMapLatest {
                if (!it)
                    flow { emit("Pending...") }
                else
                    webSocketClient.listenToSocket()
                        .flatMapLatest { result ->
//                            if(preferences.token.isNotEmpty())
//                                refreshService.refreshToken()

                            val formattedTime = DateTimeFormatter
                                .ofPattern("dd-MM-yyyy, hh:mm:ss")
                                .format(LocalDateTime.now())

                            flow {
                                emit(
                                    when (result) {
                                        is Result.Error -> when (result.error) {
                                            DataError.Network.SOCKET_ERROR -> "Cannot establish connection"
                                            else -> "Problem occurred"
                                        }

                                        is Result.Success -> {
                                            uploadFloatsService.uploadFloat()
                                            "Latest WS Msg: ${result.data}, $formattedTime"
                                        }
                                    }
                                )
                            }
                        }
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope, SharingStarted.Lazily, ""
            )

    fun manageWebSocketConnection() {
        if (_isConnectedToSocket.value)
            disconnectFromSocket()
        else
            connectToSocket()
    }

    private fun connectToSocket() {
        viewModelScope.launch {
            if (!preferences.token.firstOrNull().isNullOrBlank())
                refreshService.refreshToken()
            uploadFloatsService.uploadFloat()
            _isConnectedToSocket.value = true
        }
    }

    private fun disconnectFromSocket() {
        _isConnectedToSocket.update { false }
        viewModelScope.launch {
            webSocketClient.disconnect()
        }
    }
}
