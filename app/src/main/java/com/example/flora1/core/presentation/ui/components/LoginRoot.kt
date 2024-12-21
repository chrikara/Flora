package com.example.flora1.core.presentation.ui.components

import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flora1.R
import com.example.flora1.core.presentation.ui.toast.showSingleToast
import com.example.flora1.core.presentation.ui.uikit.buttons.PrimaryButton
import com.example.flora1.core.presentation.ui.uikit.checkboxes.CheckboxWithTitle

@Composable
fun LoginRoot(
    modifier: Modifier = Modifier,
    onRegisterClicked: (String, String, Boolean) -> Unit,
    onLoginClicked: (String, String) -> Unit,
    onContinueAsAnonymous: () -> Unit,
) {
    var isRegistering by remember {
        mutableStateOf(false)
    }

    BackHandler(
        enabled = isRegistering,
        onBack = { isRegistering = false }
    )
    Column(
        modifier = modifier
            .imePadding(),
    ) {
        if (isRegistering)
            RegisterContent(
                onRegisterClicked = onRegisterClicked,
                onBackToLogin = { isRegistering = false }
            )
        else
            LoginContent(
                onRegisterTextClicked = { isRegistering = true },
                onLoginClicked = onLoginClicked,
                onContinueAsAnonymous = onContinueAsAnonymous,
            )
    }

}

@Composable
private fun ColumnScope.LoginContent(
    onRegisterTextClicked: () -> Unit,
    onLoginClicked: (String, String) -> Unit,
    onContinueAsAnonymous: () -> Unit,
) {
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

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

    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clickable(onClick = onRegisterTextClicked),
        text = "Not yet registered? Register now.",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(100.dp))

    PrimaryButton(
        text = "Login",
        onClick = {
            when {
                !email.isValidEmail -> context.showSingleToast("Your email is incorrect.")
                password.length <= 8 -> context.showSingleToast("Password should have more than 8 chars")
                else -> onLoginClicked(email.trim(), password.trim())
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
            text = "Or",
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clickable(onClick = onContinueAsAnonymous),
        text = "Continue as an anonymous user",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    )

}

@Composable
private fun ColumnScope.RegisterContent(
    onRegisterClicked: (String, String, Boolean) -> Unit,
    onBackToLogin: () -> Unit,
) {
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var reTypedPassword by remember {
        mutableStateOf("")
    }

    var isConsentGranted by remember {
        mutableStateOf(false)
    }

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

    Spacer(modifier = Modifier.height(10.dp))

    CheckboxWithTitle(
        checked = isConsentGranted,
        onClick = { isConsentGranted = !isConsentGranted },
        text = "By signing up, I consent to giving my information for machine learning purposes (will not actually send in testing environment).",
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(modifier = Modifier.height(100.dp))

    PrimaryButton(
        text = "Register",
        onClick = {
            when {
                !email.isValidEmail -> context.showSingleToast("Your email is incorrect.")
                password.length <= 8 -> context.showSingleToast("Password should have more than 8 chars")
                password != reTypedPassword -> context.showSingleToast("Passwords don't match.")
                else -> onRegisterClicked(email.trim(), password.trim(), isConsentGranted)
            }
        }
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

