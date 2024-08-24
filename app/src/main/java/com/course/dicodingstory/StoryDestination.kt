package com.course.dicodingstory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

/**
 *hrahm,29/07/2024, 20:38
 **/
sealed interface StoryDestination {
    val icon: ImageVector
    val route: String
}

data object RegisterScreen : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Create
    override val route: String = "Register"
}

data object LoginScreen : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Send
    override val route: String = "Login"
}


data object StoryScreen : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Star
    override val route: String = "Story"
}

data object StoryScreenPaging : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Person
    override val route: String = "StoryPaging"
}

data object AddStoryScreen : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Add
    override val route: String = "AddStoryScreen"
}
data object CameraScreen : StoryDestination {
    override val icon: ImageVector = Icons.Filled.Add
    override val route: String = "camera"
}


// Screens to be displayed in the top RallyTabRow
val StoryScreens =
    listOf(LoginScreen, RegisterScreen, StoryScreenPaging, StoryScreen, AddStoryScreen,CameraScreen)
