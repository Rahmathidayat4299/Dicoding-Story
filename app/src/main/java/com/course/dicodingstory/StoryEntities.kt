package com.course.dicodingstory

/**
 *hrahm,18/07/2024, 12:39
 **/
data class RegisterEntities(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
)

data class LoginEntities(
    val email: String? = null,
    val password: String? = null,
)

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val dataLogin: LoginEntities,
    val loginResult: LoginResult? = null,
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String,
)

data class RegisterResponse(
    val error: Boolean,
    val message: String,
    val dataRegister: RegisterEntities,
)

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>
)

data class Location(
    val lat: Double,
    val lon: Double,
)

data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
)
