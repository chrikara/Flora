package com.example.flora1.features.profile.mydoctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SegmentedButtonDefaults.IconSize
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.designsystem.OpenSans
import com.example.flora1.domain.mydoctors.model.Doctor
import com.example.flora1.domain.mydoctors.model.DoctorStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorItem(
    modifier: Modifier = Modifier,
    doctor: Doctor,
    onButtonClicked: (Doctor, DoctorStatus) -> Unit,
) {
    val itemShape = RoundedCornerShape(10.dp)
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, itemShape)
            .clip(itemShape)
            .padding(15.dp),
    ) {
        Row(

            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            Column {
                Text(
                    modifier = Modifier.padding(end = 30.dp),
                    text = stringResource(id = R.string.doctor_item_name_text, doctor.name),
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Text(
                    text = doctor.updatedAt.toDoctorText(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = OpenSans,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        val buttonBackground = when (doctor.status) {
            DoctorStatus.REVOKED -> MaterialTheme.colorScheme.primary
            else -> Color(0xFF1BAA1B)
        }

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val statusList = DoctorStatus.uiDoctorStatuses
            statusList.forEachIndexed { index, doctorStatus ->
                val selected = doctorStatus == doctor.status
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = statusList.size,
                    ),
                    onClick = {
                        if (!selected)
                            onButtonClicked(doctor, doctorStatus)
                    },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = buttonBackground,
                    ),
                    selected = selected,
                    icon = {
                        SegmentedButtonDefaults.Icon(
                            active = selected,
                            activeContent = {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(IconSize),
                                    tint = MaterialTheme.colorScheme.onPrimary

                                )
                            }
                        )
                    },
                    label = {
                        Text(
                            text = doctorStatus.toResourceString(),
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = if (selected)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                    }
                )

            }

        }
    }
}

@Composable
private fun DoctorStatus.toResourceString() =
    when (this) {
        DoctorStatus.REQUESTED -> stringResource(id = R.string.requested)
        DoctorStatus.GRANTED -> stringResource(R.string.granted)
        DoctorStatus.REVOKED -> stringResource(R.string.revoked)
    }

private fun LocalDateTime.toDoctorText() =

    DateTimeFormatter.ofPattern("dd LLL yyyy · hh:mm a").withLocale(Locale.getDefault())
        .format(this)


@PreviewLightDark
@Composable
private fun Preview() {
    Flora1Theme {
        DoctorItem(
            doctor = MyDoctorsViewModel.mockDoctors[1],
            onButtonClicked = { a, b -> }
        )
    }
}
