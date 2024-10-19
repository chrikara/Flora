package com.example.flora1.features.onboarding.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.modifier.applyIf
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.features.onboarding.OnBoardingScreen


@Composable
fun OnBoardingScaffold(
    modifier : Modifier = Modifier,
    verticalArrangement: Arrangement. Vertical = Arrangement. Center,
    horizontalAlignment: Alignment. Horizontal = Alignment.CenterHorizontally,
    isNextEnabled: Boolean = true,
    onNextClick: () -> Unit = {},
    isBackEnabled: Boolean = false,
    onBackClick: () -> Unit = {},
    title: String? = null,
    description: String? = null,
    buttonText : String = "Next",
    selectedScreen : OnBoardingScreen,
    middleContent: @Composable (ColumnScope.() -> Unit),
) {
    OnBoardingScaffold(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        isNextEnabled = isNextEnabled,
        onNextClick = onNextClick,
        isBackEnabled = isBackEnabled,
        onBackClick = onBackClick,
        title = title,
        description = description,
        middleContent = middleContent,
        selectedScreen = selectedScreen,
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.ime.union(WindowInsets.navigationBars))
                    .fillMaxWidth(),
                enabled = isNextEnabled,
                text = buttonText,
                onClick = onNextClick,
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OnBoardingScaffold(
    modifier : Modifier = Modifier,
    verticalArrangement: Arrangement. Vertical = Arrangement. Center,
    horizontalAlignment: Alignment. Horizontal = Alignment.CenterHorizontally,
    isNextEnabled: Boolean = true,
    onNextClick: () -> Unit,
    isBackEnabled: Boolean = false,
    onBackClick: () -> Unit,
    title: String? = null,
    description: String? = null,
    bottomBar: @Composable () -> Unit,
    selectedScreen : OnBoardingScreen,
    middleContent: @Composable (ColumnScope.() -> Unit),
) {
    BackHandler(
        enabled = !isBackEnabled,
        onBack = {},
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 15.dp, vertical = 10.dp)
        ,
        topBar = {
            OnBoardingTopBar(
                isNextEnabled = isNextEnabled,
                onNextClick = onNextClick,
                title = title,
                description = description,
                isBackEnabled = isBackEnabled,
                onBackClick = onBackClick,
                selectedScreen = selectedScreen,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 15.dp),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                content = middleContent,
            )
        },
        bottomBar = bottomBar,
    )
}

@Composable
private fun OnBoardingTopBar(
    isBackEnabled: Boolean,
    onBackClick: () -> Unit,
    isNextEnabled: Boolean,
    onNextClick: () -> Unit,
    title: String?,
    selectedScreen : OnBoardingScreen,
    description: String?,
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        OnBoardingLinearProgressIndicator(
            selectedScreen = selectedScreen,
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val size = 30.dp
            Icon(
                modifier = Modifier
                    .testTag(stringResource(R.string.icon_back_test_tag))
                    .size(size)
                    .clip(CircleShape)
                    .alpha(if (isBackEnabled) 1f else 0f)
                    .applyIf(
                        enabled = isBackEnabled,
                        modifier = Modifier.clickable(onClick = onBackClick)
                    )
                    .align(Alignment.Top),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "",
            )
            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(id = R.drawable.flora_logo_new),
                contentDescription = ""
            )

            Icon(
                modifier = Modifier
                    .testTag(stringResource(R.string.icon_next_test_tag))
                    .size(size)
                    .clip(CircleShape)
                    .alpha(if (isNextEnabled) 1f else 0f)
                    .applyIf(
                        enabled = isNextEnabled,
                        modifier = Modifier.clickable(onClick = onNextClick)
                    )
                    .align(Alignment.Top),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = ""
            )
        }


        title?.let { title ->
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }


        description?.let { description ->
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}
