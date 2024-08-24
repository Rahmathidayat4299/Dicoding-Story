package com.course.dicodingstory.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.course.dicodingstory.component.ImageCaptureScreen
import com.course.dicodingstory.util.CameraPreview
import com.course.dicodingstory.util.takePhoto
import java.net.URI

/**
 *hrahm,16/08/2024, 23:31
 **/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraScreenTest(
    navController: NavController,
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController
) {
    val CAMERAX_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // State untuk melacak apakah izin telah diberikan
    var permissionsGranted by remember { mutableStateOf(false) }


    // Fungsi untuk memeriksa apakah izin yang diperlukan telah diberikan
    fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted = permissions.all { it.value }
        if (permissionsGranted) {
            controller.bindToLifecycle(lifecycleOwner)
        }
    }

    // Memeriksa izin saat pertama kali composable ini dimulai
    LaunchedEffect(Unit) {
        if (!hasRequiredPermissions()) {
            launcher.launch(CAMERAX_PERMISSIONS)
        } else {
            permissionsGranted = true
            controller.bindToLifecycle(lifecycleOwner)
        }
    }

    // Menampilkan pratinjau kamera hanya jika izin telah diberikan
    if (permissionsGranted) {
        val imageCapture = remember { ImageCapture.Builder().build() }
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(
                controller = controller,
                modifier = modifier.fillMaxSize()
            )
            IconButton(onClick = {
                controller.cameraSelector =
                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
            }) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Camera Switch Icon"
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {
                    takePhoto(controller,imageCapture,context,navController)
                }) {
                    Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "photo")
                }
            }
        }
    } else {
        // Tambahkan fallback UI (misalnya loading) jika diperlukan
        Box(modifier = Modifier.fillMaxSize()) {
            // Tampilan loading atau informasi bahwa izin sedang diminta
            Text(text = "Requesting camera permissions...")
        }
    }
}




