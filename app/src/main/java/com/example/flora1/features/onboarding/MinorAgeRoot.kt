package com.example.flora1.features.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flora1.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MinorAgeRoot(
    onBack : () -> Unit,
) {

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val size = 30.dp
            Icon(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .align(Alignment.Top),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "",
            )
            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = R.drawable.flora_logo),
                contentDescription = ""
            )

            Icon(
                modifier = Modifier.size(size).alpha(0f),
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "Uh oh!",
            fontFamily = FontFamily(Font(R.font.raleway_bold)),
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "You must be at least 13 years old to use Flora as per the European instructions. Sorry for any" +
                    " inconvenience but everyone should abide by the law for a happy life :) \n \nIn the meantime, you can...",
            fontFamily = FontFamily(Font(R.font.raleway_regular)),
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )
    }
}
