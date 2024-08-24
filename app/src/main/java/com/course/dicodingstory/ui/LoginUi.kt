package com.course.dicodingstory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.course.dicodingstory.LoginResponse
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.util.Preferences
import com.course.dicodingstory.util.ResultWrapper
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


/**
 *hrahm,26/07/2024, 15:01
 **/
@Composable
fun LoginUi(
    onClickToStoryScreen: () -> Unit = {},
    onClickToRegisterScreen: () -> Unit = {},
    loginViewModel: RegisterViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var registerResult by remember { mutableStateOf<ResultWrapper<LoginResponse>?>(null) }
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        loginViewModel.login.collect { result ->
            registerResult = result
            isLoading = result is ResultWrapper.Loading
            when (result) {
                is ResultWrapper.Success -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "login Success"
                        )

                    }
                }

                is ResultWrapper.Error -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Register Failed: ${result.payload?.message}"
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login", modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Icon Email")
            },
            placeholder = { Text(text = "Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon"
                )
            },
            placeholder = { Text(text = "Enter your password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Blue),
            onClick = {
                loginViewModel.login(
                    email.value,
                    password.value
                )
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp), // Adjust size as needed
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Login",
                    color = Color.White
                )
            }
        }


        ClickableText(
            text = AnnotatedString("Don't have an account? Register"),
            onClick = {
                onClickToRegisterScreen()
            },
            modifier = Modifier.padding(top = 16.dp)
        )

        registerResult?.let {
            when (it) {
                is ResultWrapper.Success -> {
                    val token = it.payload?.loginResult?.token.toString()
                    Preferences.saveToken(token, context)
                    Text(
                        text = "Login successful: ${it.payload?.message},  Bearer${token}",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    onClickToStoryScreen()
                }

                is ResultWrapper.Error -> {
                    Text(
                        text = "Registration failed: ${it.exception?.message}",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
fun DefaultpreviewLogin() {
    LoginUi(
        onClickToStoryScreen = {},
        onClickToRegisterScreen = {},
        loginViewModel = koinViewModel()
    )
}
