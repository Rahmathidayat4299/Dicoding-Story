package com.course.dicodingstory.util

/**
 *hrahm,05/08/2024, 21:55
 **/
sealed class UiState<out T> {
    data class Success<T>(val data: T?) : UiState<T>()
    data class Error<T>(val message: String) : UiState<T>()
    object Loading : UiState<Nothing>()

}