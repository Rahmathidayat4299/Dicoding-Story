package com.course.dicodingstory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.course.dicodingstory.component.ImageCaptureScreen
import com.course.dicodingstory.ui.AddStoryScreen
import com.course.dicodingstory.ui.CameraScreenTest
import com.course.dicodingstory.ui.LoginUi
import com.course.dicodingstory.ui.RegisterUi
import com.course.dicodingstory.ui.StoryScreenPagingscreen

/**
 *hrahm,29/07/2024, 21:11
 **/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StoryNavHost(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
            )
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = LoginScreen.route) {
            LoginUi(
                onClickToStoryScreen = {
                    navController.navigate(StoryScreen.route)
                },
                onClickToRegisterScreen = {
                    navController.navigate(RegisterScreen.route)
                }
            )
        }
        composable(route = RegisterScreen.route) {
            RegisterUi(
                onClickSeeAllAccounts = {
                    navController.navigate(LoginScreen.route)
                }
            )
        }

        composable(route = StoryScreen.route) {
            StoryScreenPagingscreen()
        }
        composable(route = StoryScreenPaging.route) {
            StoryScreenPagingscreen()
        }
        composable(route = AddStoryScreen.route) {
            AddStoryScreen()
        }
        composable(route = CameraScreen.route) {
            CameraScreenTest(navController = navController, controller = controller)
        }
        composable(
            route = "image_capture/{imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")?.toUri()
            imageUri?.let { ImageCaptureScreen(it) }
        }

    }
}

