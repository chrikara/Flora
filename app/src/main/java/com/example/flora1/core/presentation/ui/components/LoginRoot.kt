package com.example.flora1.core.presentation.ui.components

import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flora1.R
import com.example.flora1.core.presentation.designsystem.DisabledAlphaBackground
import com.example.flora1.core.presentation.designsystem.Flora1Theme
import com.example.flora1.core.presentation.ui.observers.ObserveAsEvents
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.core.presentation.ui.uikit.buttons.BackButton
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.features.onboarding.usernameage.UsernameAgeEvent
import com.example.flora1.features.onboarding.usernameage.UsernameAgeViewModel

@Composable
fun LoginRoot(
    onSuccessfulLogin: () -> Unit,
    onContinueAsAnonymous: () -> Unit,
    onBackClicked: (() -> Unit)?,
    viewModel: UsernameAgeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isRunning by viewModel.isRunning.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            UsernameAgeEvent.RegistrationSuccessful -> {
                context.showSingleToast(context.getString(R.string.registration_was_successful))
                onSuccessfulLogin()
            }

            is UsernameAgeEvent.RegistrationFailed -> context.showSingleToast(event.message)
            is UsernameAgeEvent.LoginFailed -> context.showSingleToast(event.message)
            UsernameAgeEvent.LoginSuccessful -> {
                context.showSingleToast(context.getString(R.string.login_was_successful))
                onSuccessfulLogin()
            }
        }
    }

    LoginRoot(
        modifier = Modifier.fillMaxSize(),
        onRegisterClicked = viewModel::onRegister,
        onLoginClicked = { username, password ->
            viewModel.onLogin(
                username = username,
                password = password,
            )
        },
        onContinueAsAnonymous = onContinueAsAnonymous,
        isRunning = isRunning,
        onBackClicked = onBackClicked,
    )
}

@Composable
fun LoginRoot(
    modifier: Modifier = Modifier,
    onRegisterClicked: (String, String, String) -> Unit,
    onLoginClicked: (String, String) -> Unit,
    onContinueAsAnonymous: () -> Unit,
    onBackClicked: (() -> Unit)? = null,
    isRunning: Boolean,
) {
    var isRegistering by remember {
        mutableStateOf(false)
    }

    BackHandler(
        enabled = isRegistering,
        onBack = { isRegistering = false }
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            if (isRegistering)
                RegisterContent(
                    onRegisterClicked = onRegisterClicked,
                    onBackToLogin = { isRegistering = false },
                    isRunning = isRunning,
                )
            else
                LoginContent(
                    onRegisterTextClicked = { isRegistering = true },
                    onLoginClicked = onLoginClicked,
                    onContinueAsAnonymous = onContinueAsAnonymous,
                    onBackClicked = onBackClicked,
                    isRunning = isRunning,
                )
        }
    }

}

@Composable
private fun ColumnScope.LoginContent(
    isRunning: Boolean,
    onRegisterTextClicked: () -> Unit,
    onLoginClicked: (String, String) -> Unit,
    onContinueAsAnonymous: () -> Unit,
    onBackClicked: (() -> Unit)? = null,
) {
    val context = LocalContext.current

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    Spacer(modifier = Modifier.height(32.dp))

    Box(modifier = Modifier.fillMaxWidth()) {
        if (onBackClicked != null)
            BackButton(
                onClick = onBackClicked,
            )

        Image(
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.flora_logo_new),
            contentDescription = ""
        )
    }


    Spacer(modifier = Modifier.height(32.dp))

    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(id = R.string.login_with_your_account),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(
            password = password,
            onPasswordChanged = { password = it },
            labelText = "Password"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onRegisterTextClicked),
            text = stringResource(R.string.register_now),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(100.dp))

        PrimaryButton(
            text = "Login",
            leadingIcon = if (!isRunning) null else {
                {
                    FloraButtonProgressIndicator()
                }
            },
            enabled = !isRunning,
            onClick = {
                when {
                    password.length <= 8 -> context.showSingleToast("Password should have more than 8 chars")
                    else -> onLoginClicked(username.trim(), password.trim())
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.or),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onContinueAsAnonymous),
            text = stringResource(R.string.continue_as_an_anonymous_user),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

    }

}

@Composable
private fun ColumnScope.RegisterContent(
    isRunning: Boolean,
    onRegisterClicked: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
) {
    val context = LocalContext.current

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var reTypedPassword by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = username,
        singleLine = true,
        onValueChange = { username = it },
        label = {
            Text(text = "Username")
        }
    )
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        singleLine = true,
        onValueChange = { email = it },
        label = {
            Text(text = "E-mail")
        }
    )
    Spacer(modifier = Modifier.height(10.dp))

    PasswordTextField(
        password = password,
        onPasswordChanged = { password = it },
        labelText = "Password"
    )


    Spacer(modifier = Modifier.height(10.dp))

    PasswordTextField(
        password = reTypedPassword,
        onPasswordChanged = { reTypedPassword = it },
        labelText = "Re-type password"
    )
    Spacer(modifier = Modifier.height(100.dp))

    PrimaryButton(
        text = "Register",
        enabled = !isRunning,
        onClick = {
            when {
                !email.isValidEmail -> context.showSingleToast("E-mail is incorrect")
                password.length <= 8 -> context.showSingleToast("Password should have more than 8 chars")
                password != reTypedPassword -> context.showSingleToast("Passwords don't match.")
                else -> onRegisterClicked(
                    username.trim(),
                    email.trim(),
                    password.trim(),
                )
            }
        },
        leadingIcon = if (!isRunning) null else {
            {
                FloraButtonProgressIndicator()
            }
        },
    )
    Spacer(modifier = Modifier.height(30.dp))

    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clickable(onClick = onBackToLogin),
        text = "Back to Login",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    )
}

val CharSequence.isValidEmail: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()


@Composable
private fun PasswordTextField(
    labelText: String,
    password: String,
    onPasswordChanged: (String) -> Unit,
) {
    var passwordToggled by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        singleLine = true,
        onValueChange = onPasswordChanged,
        label = {
            Text(text = labelText)
        },
        visualTransformation = if (passwordToggled)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        trailingIcon = {
            IconButton(
                modifier = Modifier
                    .size(20.dp),
                onClick = {
                    passwordToggled = !passwordToggled

                },
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(
                        id =
                        if (passwordToggled)
                            R.drawable.closed_eye
                        else
                            R.drawable.password
                    ),
                    contentDescription = "",
                )
            }
        }

    )
}

@Composable
fun FloraButtonProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(30.dp),
        color = MaterialTheme.colorScheme.primary.copy(DisabledAlphaBackground)
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    Flora1Theme {
        LoginRoot(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onRegisterClicked = { s: String, s1: String, s2: String -> },
            onLoginClicked = { s: String, s1: String -> },
            onContinueAsAnonymous = {},
            isRunning = false,
        )
    }

}
