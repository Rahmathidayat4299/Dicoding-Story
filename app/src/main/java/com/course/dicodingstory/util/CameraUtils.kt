package com.course.dicodingstory.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

/**
 *hrahm,15/08/2024, 17:08
 **/
fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: ImageCapture,
    context: Context,
    navController: NavController
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                // Save Bitmap to file and get URI
                val savedUri = saveBitmapToFile(context, rotatedBitmap)

                // Pass the URI to the onPhotoTaken callback
                if (savedUri != null) {
                    val encodedUri = Uri.encode(savedUri.toString())
                    Log.d("CameraCapture", "Navigating to image_capture/$encodedUri")
                    navController.navigate("image_capture/$encodedUri")
                }

                // Close the ImageProxy to release resources
                image.close()
                Log.e("Camera", "Succes get Photo")
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}
fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    val name = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    return uri
}


