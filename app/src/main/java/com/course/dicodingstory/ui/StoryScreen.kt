package com.course.dicodingstory.ui

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.Story
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.component.CardElevationStory
import com.course.dicodingstory.component.ProgressLoader
import com.course.dicodingstory.util.UiState
import org.koin.androidx.compose.koinViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * hrahm, 03/08/2024, 18:49
 **/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StoryScreen(
    storyViewModel: RegisterViewModel = koinViewModel()
) {
    val state by storyViewModel.getStory.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        // Trigger the loading of stories
        storyViewModel.getStories()
    }

    Column {
        when (state) {
            is UiState.Success -> {
                ProgressLoader(isLoading = false)
                val stories = (state as UiState.Success<StoryResponse>).data?.listStory
                if (stories != null) {
                    ListStories(stories)
                }
            }

            is UiState.Error -> {
                ProgressLoader(isLoading = false)
            }

            is UiState.Loading -> {
                ProgressLoader(isLoading = true)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListStories(
    stories: List<Story>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier.padding(vertical = 8.dp)
    ) {
        items(stories) { story ->
            Log.e(TAG, "ListStories: ${story.createdAt}")
            val formattedDateTime = formatDateTime(story.createdAt)
            CardElevationStory(story.name, story.photoUrl, formattedDateTime)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalStdlibApi::class)
fun formatDateTime(dateTimeString: String): String {
    // Define the formatter for the given string pattern with the time zone
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

    // Parse the string to an OffsetDateTime using the formatter
    val parsedDateTime = OffsetDateTime.parse(dateTimeString, inputFormatter)

    // Define the formatter for the desired output pattern
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    // Format the parsed OffsetDateTime to the desired pattern
    return parsedDateTime.format(outputFormatter)
}

