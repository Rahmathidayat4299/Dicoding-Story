package com.course.dicodingstory.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.course.dicodingstory.LoginEntities
import com.course.dicodingstory.LoginResponse
import com.course.dicodingstory.RegisterEntities
import com.course.dicodingstory.RegisterResponse
import com.course.dicodingstory.Story
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.model.RepositoryStoryDicoding
import com.course.dicodingstory.model.StoryPagingSource
import com.course.dicodingstory.util.ResultWrapper
import com.course.dicodingstory.util.UiState
import com.course.dicodingstory.util.UiState.Error
import com.course.dicodingstory.util.UiState.Loading
import com.course.dicodingstory.util.UiState.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.io.File

/**
 *hrahm,19/07/2024, 19:33
 **/
class RegisterViewModel(
    private val repository: RepositoryStoryDicoding,
    private val pagingSource: StoryPagingSource
) : ViewModel() {
    private val _register = MutableSharedFlow<UiState<RegisterResponse>>()
    val register: SharedFlow<UiState<RegisterResponse>>
        get() = _register
    private val _postStories = MutableSharedFlow<UiState<StoryResponse>>()
    val postStories: SharedFlow<UiState<StoryResponse>>
        get() = _postStories

    //login
    private val _login = MutableSharedFlow<ResultWrapper<LoginResponse>>()
    val login: SharedFlow<ResultWrapper<LoginResponse>>
        get() = _login

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.register(
                registerEntities = RegisterEntities(
                    name = name,
                    email = email,
                    password = password
                )
            ).collect {
                when (it) {
                    is Success -> {
                        _register.emit(Success(it.data))
                    }

                    is Loading -> {
                        _register.emit(Loading)
                    }

                    is Error -> {
                        _register.emit(Error(it.message))
                    }
                }
            }
        }
    }

    fun postStories(
        description: String,
        imageMultipart: File,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postStories(description, imageMultipart).collect {
                when (it) {
                    is Success -> {
                        _postStories.emit(Success(it.data))
                    }

                    is Loading -> {
                        _postStories.emit(Loading)
                    }

                    is Error -> {
                        _postStories.emit(Error(it.message))
                    }
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login(
                loginEntities = LoginEntities(
                    email = email,
                    password = password
                )
            ).collect {
                _login.emit(it)
            }
        }
    }

    val _getStory = MutableStateFlow<UiState<StoryResponse>>(Loading)
    val getStory: StateFlow<UiState<StoryResponse>> = _getStory
    fun getStories() = viewModelScope.launch(Dispatchers.IO) {
        supervisorScope {
            repository.getStories(1, 50, 1).collect {
                when (it) {
                    is Success -> {
                        _getStory.value = Success(it.data)
                    }

                    is Loading -> {
                        _getStory.value = Loading
                    }

                    is Error -> {
                        //Handle Error
                        _getStory.value = Error(it.message)
                    }
                }
            }
        }
    }

    private val _storyResponse = MutableStateFlow<PagingData<Story>>(PagingData.empty())
    var storyResponse = _storyResponse.asStateFlow()
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(10, enablePlaceholders = true)
            ) {
                pagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _storyResponse.value = it
            }
        }
    }

}