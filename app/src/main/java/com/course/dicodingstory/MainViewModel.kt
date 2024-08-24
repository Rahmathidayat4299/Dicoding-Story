package com.course.dicodingstory

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *hrahm,17/08/2024, 22:04
 **/
class MainViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Uri>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(uri: Uri) {
        _bitmaps.value += uri
    }
}