package com.course.dicodingstory.model

import com.course.dicodingstory.LoginEntities
import com.course.dicodingstory.LoginResponse
import com.course.dicodingstory.RegisterEntities
import com.course.dicodingstory.RegisterResponse
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.util.ResultWrapper
import com.course.dicodingstory.util.UiState
import com.course.dicodingstory.util.proceedFlow
import com.course.dicodingstory.util.toResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 *hrahm,19/07/2024, 19:29
 **/
interface RepositoryStoryDicoding {
    suspend fun register(
        registerEntities: RegisterEntities
    ): Flow<UiState<RegisterResponse>>

    suspend fun login(
        loginEntities: LoginEntities
    ): Flow<ResultWrapper<LoginResponse>>

    suspend fun getStories(
        page: Int,
        size: Int,
        location: Int
    ): Flow<UiState<StoryResponse>>

    suspend fun postStories(
        description: String,
        imageMultipart: File,
    ): Flow<UiState<StoryResponse>>
}

class RepositoryStoryDicodingImpl(
    private val remoteDataSource: RemoteDataSource
) : RepositoryStoryDicoding {
    override suspend fun register(
        registerEntities: RegisterEntities
    ): Flow<UiState<RegisterResponse>> {
        return toResultFlow {
            remoteDataSource.register(registerEntities)
        }
    }

    override suspend fun login(loginEntities: LoginEntities): Flow<ResultWrapper<LoginResponse>> {
        return proceedFlow {
            remoteDataSource.login(loginEntities)
        }
    }

    override suspend fun getStories(
        page: Int,
        size: Int,
        location: Int
    ): Flow<UiState<StoryResponse>> {
        return toResultFlow {
            remoteDataSource.getStories(page, size, location)
        }
    }

    override suspend fun postStories(
        description: String,
        imageMultipart: File
    ): Flow<UiState<StoryResponse>> = flow {
        emit(UiState.Loading)
        try {
            val desc = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageMultipart.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                imageMultipart.name,
                requestImageFile
            )
            val response = remoteDataSource.postStories(desc, imageMultipart)

            if (response.isSuccessful) {
                emit(UiState.Success(response.body()))
            } else {
                emit(UiState.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.toString()))
        }
    }


}