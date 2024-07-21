package com.course.dicodingstory.model

import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.util.ResultWrapper
import com.course.dicodingstory.util.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
 *hrahm,19/07/2024, 19:29
 **/
interface RepositoryStoryDicoding {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<StoryResponse>>
}

class RepositoryStoryDicodingImpl(
    private val remoteDataSource: RemoteDataSource
) : RepositoryStoryDicoding {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<StoryResponse>> {
        return proceedFlow {
            remoteDataSource.Register(name, email, password)
        }
    }

}