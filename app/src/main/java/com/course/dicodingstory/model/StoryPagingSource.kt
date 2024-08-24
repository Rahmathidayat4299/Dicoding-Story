package com.course.dicodingstory.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.course.dicodingstory.Story
import retrofit2.HttpException
import java.io.IOException

/**
 *hrahm,10/08/2024, 21:32
 **/
class StoryPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        val page = params.key ?: 1
        val response = remoteDataSource.getStoriesPaging(page, params.loadSize)
        val listStory = response.listStory
        return try {
            LoadResult.Page(
                data = listStory,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (listStory.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }
    }

}