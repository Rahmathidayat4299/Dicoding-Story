package com.course.dicodingstory.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *hrahm,19/07/2024, 18:32
 **/
@Composable
fun TextFieldComponent(
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textState: MutableState<TextFieldValue>
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        label = { Text(text = label) },
        onValueChange = {
            text = it
        },
        modifier = Modifier.padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val nameState = remember { mutableStateOf(TextFieldValue("")) }
    TextFieldComponent(
        label = "Input value",
        keyboardType = KeyboardType.Text,
        textState = nameState
    )
}
