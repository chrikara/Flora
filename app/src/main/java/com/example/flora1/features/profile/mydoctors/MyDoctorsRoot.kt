package com.example.flora1.features.profile.mydoctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.flow.collectSuccessAsState
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.observers.ObserveAsEvents
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.core.presentation.ui.uikit.ErrorScreen
import com.example.flora1.core.presentation.ui.uikit.LoadingScreen
import com.example.flora1.core.presentation.ui.uikit.buttons.CircleCloseButton
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus
import com.example.flora1.domain.util.isError
import com.example.flora1.domain.util.isRunning

@Composable
fun MyDoctorsContent(
    doctorsViewModel: MyDoctorsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val doctorsResult by doctorsViewModel.doctors.collectAsStateWithLifecycle()
    val doctors by doctorsViewModel.doctors.collectSuccessAsState(emptyList())
    val selectedAddress by doctorsViewModel.selectedAddress.collectAsStateWithLifecycle()

    ObserveAsEvents(doctorsViewModel.events) { event ->
        when (event) {
            DoctorEvent.NavigateBack -> onBack()
            DoctorEvent.SignInError ->
                context.showSingleToast(R.string.no_signature_connect_to_metamask_first)

            DoctorEvent.DoctorRejected ->
                context.showSingleToast(context.getString(R.string.doctor_was_rejected))

            is DoctorEvent.InvalidAddress ->
                context.showSingleToast(
                    context.getString(
                        R.string.wrong_account_yours_is,
                        event.selectedAddress,
                    ),
                )
        }
    }

    MyDoctorsRoot(
        doctors = doctors,
        onBack = doctorsViewModel::onBack,
        onDoctorButtonClicked = doctorsViewModel::updateDoctorStatus,
        isRunning = doctorsResult.isRunning,
        isError = doctorsResult.isError,
        onRetry = doctorsViewModel::onRetry,
        selectedAddress = selectedAddress,
    )
}

@Composable
fun MyDoctorsRoot(
    isError: Boolean = false,
    isRunning: Boolean = false,
    onRetry: () -> Unit,
    onBack: () -> Unit = {},
    doctors: List<Doctor> = listOf(),
    selectedAddress: String,
    onDoctorButtonClicked: (Doctor, newStatus: DoctorStatus) -> Unit = { a, b -> },
) {
    when {
        isError -> ErrorScreen(
            onRetryButtonClicked = onRetry,
        )

        isRunning -> LoadingScreen()

        else -> MyDoctorsContent(
            onBack = onBack,
            doctors = doctors,
            onDoctorButtonClicked = onDoctorButtonClicked,
            selectedAddress = selectedAddress,
        )
    }
}

@Composable
internal fun MyDoctorsContent(
    onBack: () -> Unit,
    doctors: List<Doctor> = listOf(),
    selectedAddress: String,
    onDoctorButtonClicked: (Doctor, newStatus: DoctorStatus) -> Unit = { a, b -> },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(vertical = 30.dp),
        ) {
            CircleCloseButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack,
            )

            var titleWidth by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current

            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .onSizeChanged {
                            with(density) {
                                titleWidth = it.width.toDp()
                            }
                        },
                    text = "My Doctors",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                if (selectedAddress.isNotBlank())
                    Text(
                        modifier = Modifier.width(titleWidth + 45.dp),
                        text = selectedAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
            }

        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            item(key = "doctorsHeader") {

                Text(
                    text = pluralStringResource(
                        id = R.plurals.my_doctors_description,
                        count = doctors.size,
                        doctors.size,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            items(
                items = doctors,
                key = Doctor::name,
            ) { doctor ->
                DoctorItem(
                    doctor = doctor,
                    onButtonClicked = onDoctorButtonClicked,
                )
            }

            item(key = "navigationSpacer") {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    Flora1Theme {
        var doctors by remember {
            mutableStateOf(MyDoctorsViewModel.mockDoctors)
        }

        MyDoctorsContent(
            onBack = {},
            doctors = doctors,
            selectedAddress = "0xAAjkfdjfsjdifjsidjfpsdigsjdgijsdp",
            onDoctorButtonClicked = { doctor, newStatus ->
                doctors = doctors.map {
                    if (doctor == it)
                        it.copy(status = newStatus)
                    else
                        it
                }

            }
        )
    }
}
