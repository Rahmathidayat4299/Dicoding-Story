package com.course.dicodingstory.model

import com.course.dicodingstory.LoginEntities
import com.course.dicodingstory.LoginResponse
import com.course.dicodingstory.RegisterEntities
import com.course.dicodingstory.RegisterResponse
import com.course.dicodingstory.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

/**
 *hrahm,19/07/2024, 19:18
 **/
interface RemoteDataSource {
    suspend fun register(
        registerEntities: RegisterEntities
    ): Response<RegisterResponse>

    suspend fun login(
        loginEntities: LoginEntities
    ): LoginResponse

    suspend fun getStories(
        page: Int,
        size: Int,
        location: Int
    ): Response<StoryResponse>

    suspend fun getStoriesPaging(
        page: Int? = null,
        size: Int? = null,
        location: Int = 0
    ): StoryResponse

    suspend fun postStories(
        description: RequestBody,
        imageMultipart: MultipartBody.Part,
    ): Response<StoryResponse>
}

class RemoteDataSourceImpl(
    private val remoteService: RemoteService
) : RemoteDataSource {
    override suspend fun register(
        registerEntities: RegisterEntities
    ): Response<RegisterResponse> {
        return remoteService.register(
            registerEntities
        )
    }

    override suspend fun login(loginEntities: LoginEntities): LoginResponse {
        return remoteService.login(loginEntities)
    }

    override suspend fun getStories(page: Int, size: Int, location: Int) =
        remoteService.getStories(page, size, location)

    override suspend fun getStoriesPaging(
        page: Int?,
        size: Int?,
        location: Int
    ): StoryResponse = remoteService.getStoriesPaging(page, size, location)

    override suspend fun postStories(
        description: RequestBody,
        imageMultipart: MultipartBody.Part
    ): Response<StoryResponse> = remoteService.postStories(description, imageMultipart)


}