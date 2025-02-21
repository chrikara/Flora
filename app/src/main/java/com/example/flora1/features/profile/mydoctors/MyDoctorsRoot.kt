package com.example.flora1.features.profile.mydoctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.observers.ObserveAsEvents
import com.example.flora1.core.presentation.ui.uikit.buttons.CircleCloseButton
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus

@Composable
fun MyDoctorsRoot(
    doctorsViewModel: MyDoctorsViewModel = viewModel(),
    onBack: () -> Unit,
) {
    val doctors by doctorsViewModel.doctors.collectAsState()

    ObserveAsEvents(doctorsViewModel.events) { event ->
        when (event) {
            DoctorEvent.NavigateBack -> onBack()
        }
    }

    MyDoctorsRoot(
        doctors = doctors,
        onBackClicked = doctorsViewModel::onBack,
        onDoctorButtonClicked = doctorsViewModel::updateDoctorStatus,

        )
}

@Composable
internal fun MyDoctorsRoot(
    onBackClicked: () -> Unit,
    doctors: List<Doctor> = listOf(),
    onDoctorButtonClicked: (Doctor, newStatus: DoctorStatus) -> Unit = { a, b -> },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(all = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
        ) {
            CircleCloseButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackClicked,
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "My Doctors",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            items(
                items = doctors,
                key = Doctor::id,
            ) { doctor ->
                DoctorItem(
                    doctor = doctor,
                    onButtonClicked = onDoctorButtonClicked,
                )
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

        MyDoctorsRoot(
            onBackClicked = {},
            doctors = doctors,
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
