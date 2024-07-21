package com.course.dicodingstory.component


/**
 *hrahm,19/07/2024, 18:49
 **/

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.util.ResultWrapper
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterUi(registerViewModel: RegisterViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val nameState = remember { mutableStateOf(TextFieldValue("")) }
    val emailState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordState = remember { mutableStateOf(TextFieldValue("")) }

    var registerResult by remember { mutableStateOf<ResultWrapper<StoryResponse>?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        registerViewModel.register.collect { result ->
            registerResult = result
            isLoading = result is ResultWrapper.Loading
            when (result) {
                is ResultWrapper.Success -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Register Success"
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
        TextFieldComponent(
            label = "Name",
            keyboardType = KeyboardType.Text,
            textState = nameState
        )
        TextFieldComponent(
            label = "Email",
            keyboardType = KeyboardType.Email,
            textState = emailState
        )
        TextFieldComponent(
            label = "Password",
            keyboardType = KeyboardType.Password,
            textState = passwordState
        )
        TextButton(
            onClick = {
                registerViewModel.registerUser(
                    nameState.value.text,
                    emailState.value.text,
                    passwordState.value.text
                )
            }
        ) {
            Text(text = "Register")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        registerResult?.let {
            when (it) {
                is ResultWrapper.Success -> {
                    Text(text = "Registration successful: ${it.payload?.message}", modifier = Modifier.padding(top = 16.dp))
                }
                is ResultWrapper.Error -> {
                    Text(text = "Registration failed: ${it.exception?.message}", modifier = Modifier.padding(top = 16.dp))
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
fun Defaultpreview() {
    RegisterUi()
}