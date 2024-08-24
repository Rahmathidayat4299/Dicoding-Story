package com.course.dicodingstory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.component.CardElevationStory
import org.koin.androidx.compose.koinViewModel

/**
 *hrahm,10/08/2024, 21:45
 **/
@Composable
fun StoryScreenPagingscreen(modifier: Modifier = Modifier) {
    StoryScreenPaging(modifier = modifier)
}

@Composable
fun StoryScreenPaging(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val response = viewModel.storyResponse.collectAsLazyPagingItems()

    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        items(response.itemCount) {
            CardElevationStory(
                name = response[it]?.name.toString(),
                photoUrl = response[it]?.photoUrl.toString(),
                createdAt = response[it]?.createdAt.toString()
            )
        }
        response.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    item {
                        Text(text = "Error")
                    }
                }
            }
        }
    }

}