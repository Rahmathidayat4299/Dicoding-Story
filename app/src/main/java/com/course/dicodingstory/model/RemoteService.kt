package com.course.dicodingstory.model

import android.content.Context
import com.course.dicodingstory.LoginEntities
import com.course.dicodingstory.LoginResponse
import com.course.dicodingstory.RegisterEntities
import com.course.dicodingstory.RegisterResponse
import com.course.dicodingstory.StoryResponse
import com.course.dicodingstory.util.AuthInterceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


/**
 *hrahm,18/07/2024, 12:44
 **/
interface RemoteService {

    @POST("register")
    suspend fun register(
        @Body registerEntities: RegisterEntities
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body loginEntities: LoginEntities
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 0
    ): Response<StoryResponse>

    @GET("stories")
    suspend fun getStoriesPaging(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 1
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun postStories(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<StoryResponse>

    companion object {
        @JvmStatic
        operator fun invoke(
            context: Context
        ): RemoteService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context)) // Tambahkan AuthInterceptor
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RemoteService::class.java)
        }
    }
}