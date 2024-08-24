package com.course.dicodingstory.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

/**
 *hrahm,15/08/2024, 17:09
 **/


@Composable
fun ImageCaptureScreen(
    imageUri: Uri,
) {
    val decodedUri = Uri.parse(Uri.decode(imageUri.toString()))
    val description = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = decodedUri),
            contentDescription = "Captured Image",
        )
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Description, contentDescription = "Icon Email")
            },
            placeholder = { Text(text = "Enter your Caption") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )

    }
}


