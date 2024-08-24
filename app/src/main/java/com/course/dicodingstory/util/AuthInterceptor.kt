package com.course.dicodingstory.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 *hrahm,27/07/2024, 16:01
 **/
class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Ambil token dari SharedPreferences
        val sharedPref =  Preferences.initPref(context, "onSignIn")
        val token = sharedPref.getString("token", "")
        if (token != null) {
            // Tambahkan header Authorization
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}