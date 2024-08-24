@file:OptIn(ExperimentalMaterial3Api::class)

package com.course.dicodingstory

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.course.dicodingstory.component.CustomAppBar
import com.course.dicodingstory.ui.theme.DicodingStoryTheme
import com.course.dicodingstory.util.Preferences
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DicodingStoryTheme {
                StoryApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StoryApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    StoryScreens.find { it.route == currentDestination?.route } ?: LoginScreen
    val context = LocalContext.current
    val sharedPref = Preferences.initPref(context, "onSignIn")
    val token = sharedPref.getString("token", "")
    val startDestination = if (token.isNullOrEmpty()) {
        LoginScreen.route
    } else {
        StoryScreenPaging.route
    }
    Text(text = "$token")
    Scaffold(
        topBar = {
            CustomAppBar(
                onClickButton = {
                    navController.navigate(CameraScreen.route)
                }
            )
        }
    ) { innerPadding ->
        StoryNavHost(
            startDestination = startDestination,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }

}





