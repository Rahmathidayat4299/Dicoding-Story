package com.course.dicodingstory.ui


/**
 *hrahm,19/07/2024, 18:49
 **/

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.RegisterResponse
import com.course.dicodingstory.component.ProgressLoader
import com.course.dicodingstory.util.ResultWrapper
import com.course.dicodingstory.util.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterUi(
    onClickSeeAllAccounts: () -> Unit = {},
    registerViewModel: RegisterViewModel = koinViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val name = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }
    val state by registerViewModel.register.collectAsStateWithLifecycle(null)
    var registerResult by remember { mutableStateOf<ResultWrapper<RegisterResponse>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        registerViewModel.register.collect { result ->
//            registerResult = result
//            isLoading = result is ResultWrapper.Loading
//            when (result) {
//                is ResultWrapper.Success -> {
//                    coroutineScope.launch {
//                        snackbarHostState.showSnackbar(
//                            message = "Register Success"
//                        )
//                        delay(2000)
//                        onClickSeeAllAccounts()
//                    }
//
//                }
//                is ResultWrapper.Error -> {
//                    coroutineScope.launch {
//                        snackbarHostState.showSnackbar(
//                            message = "Register Failed: ${result.payload?.message}"
//                        )
//                    }
//                }
//                else -> Unit
//            }
//        }
//    }


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name Icon"
                )
            },
            placeholder = { Text(text = "Enter your name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
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
            singleLine = true
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Blue),
            onClick = {
                registerViewModel.registerUser(
                    name.value.text,
                    email.value.text,
                    password.value.text
                )
            }
        ) {
            Text(text = "Register", color = Color.White)
        }

        if (isLoading) {
            CircularProgressIndicator(progress = 1.0f)
        }
        Column {
            when (state) {
                is UiState.Loading -> {
                    ProgressLoader(isLoading = true)
                }

                is UiState.Success -> {
                    ProgressLoader(isLoading = false)
                    val registerResult = (state as UiState.Success<RegisterResponse>).data?.message
                    Text(
                        text = "Registration successful: $registerResult",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                is UiState.Error -> {
                    ProgressLoader(isLoading = false)
                    val registerResult = (state as UiState.Error<RegisterResponse>).message
                    Text(
                        text = "Registration failed: $registerResult",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                else -> {
                    // Do nothing
                }
            }
        }

//        registerResult?.let {
//            when (it) {
//                is ResultWrapper.Success -> {
//                    Text(text = "Registration successful: ${it.payload?.message}", modifier = Modifier.padding(top = 16.dp))
//                }
//                is ResultWrapper.Error -> {
//                    Text(text = "Registration failed: ${it.exception?.message}", modifier = Modifier.padding(top = 16.dp))
//                }
//                else -> {
//                    // Do nothing
//                }
//            }
//        }
    }

    SnackbarHost(hostState = snackbarHostState)
}



@Preview(showBackground = true)
@Composable
fun Defaultpreview() {
    RegisterUi()
}