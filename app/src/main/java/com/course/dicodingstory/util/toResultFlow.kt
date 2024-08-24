package com.course.dicodingstory.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 *hrahm,05/08/2024, 21:54
 **/
inline fun <reified T> toResultFlow(
    crossinline call: suspend () -> Response<T>?
): Flow<UiState<T>> {
    return flow {
            emit(UiState.Loading)
            try {
                val c = call()
                if (c?.isSuccessful == true && c.body() != null) {
                    emit(UiState.Success(c.body()))
                } else {
                    if (c != null) {
                        emit(UiState.Error(c.message()))
                    }
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.toString()))
            }
    }.flowOn(Dispatchers.IO)
}