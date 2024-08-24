package com.course.dicodingstory.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 *hrahm,13/08/2024, 08:20
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    onClickButton: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Dicoding Story")
        },
        actions = {
            Row {
                IconButton(onClick = onClickButton) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location")
                }
            }
        },
        modifier = Modifier.background(Color.Blue)
    )
}