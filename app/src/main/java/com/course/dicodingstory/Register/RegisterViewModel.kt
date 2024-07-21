package com.course.dicodingstory.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.model.RepositoryStoryDicoding
import com.course.dicodingstory.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 *hrahm,19/07/2024, 19:33
 **/
class RegisterViewModel(
    private val repository: RepositoryStoryDicoding
) : ViewModel() {
    private val _register = MutableSharedFlow<ResultWrapper<StoryResponse>>()
    val register: SharedFlow<ResultWrapper<StoryResponse>>
        get() = _register

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.register(
                name, email, password
            ).collect {
                _register.emit(it)
            }
        }
    }
}