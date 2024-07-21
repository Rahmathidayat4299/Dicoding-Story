package com.course.dicodingstory

/**
 *hrahm,18/07/2024, 12:39
 **/
data class RegisterEntities(
    val name: String,
    val email: String,
    val password: String,
)

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val data: RegisterEntities
)
