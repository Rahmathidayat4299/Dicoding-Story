package com.course.dicodingstory.model

import com.course.dicodingstory.StoryResponse

/**
 *hrahm,19/07/2024, 19:18
 **/
interface RemoteDataSource {
    suspend fun Register(
        name: String,
        email: String,
        password: String
    ): StoryResponse
}

class RemoteDataSourceImpl(
    private val remoteService: RemoteService
) : RemoteDataSource {
    override suspend fun Register(
        name: String,
        email: String,
        password: String
    ): StoryResponse {
        return remoteService.Register(
            name,
            email,
            password
        )
    }

}