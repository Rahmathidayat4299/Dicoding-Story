package com.course.dicodingstory.model

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.course.dicodingstory.BuildConfig.BASE_URL
import com.course.dicodingstory.RegisterEntities
import com.course.dicodingstory.StoryResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


/**
 *hrahm,18/07/2024, 12:44
 **/
interface RemoteService {
    @FormUrlEncoded
    @POST("register")
    fun Register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): StoryResponse

    companion object {
        @JvmStatic
        operator fun invoke(
            chucker: ChuckerInterceptor
        ): RemoteService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
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