package com.course.dicodingstory.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.FileUtils
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 *hrahm,22/08/2024, 21:33
 **/
fun getPath(context: Context, uri: Uri): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            return it.getString(columnIndex)
        }
    }
    return null
}
fun compressImage(context: Context, imageUri: Uri): File {
    val filePath = getPath(context, imageUri) // Get the real file path from the Uri
    val bitmap = BitmapFactory.decodeFile(filePath)

    // Compress the bitmap to JPEG with 50% quality
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

    // Write the compressed bitmap to a file
    val compressedFile = File(context.cacheDir, "compressed_image.jpg")
    val fos = FileOutputStream(compressedFile)
    fos.write(outputStream.toByteArray())
    fos.flush()
    fos.close()

    return compressedFile
}