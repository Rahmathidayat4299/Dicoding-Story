package com.course.dicodingstory.util

import android.content.Context
import android.content.SharedPreferences

/**
 *hrahm,27/07/2024, 15:23
 **/
object Preferences {
    fun initPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun editorPreference(context: Context, name: String): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    fun saveToken(token: String, context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.putString("token", token).apply()

    }

    fun saveName(name: String, context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.putString("name", name).apply()

    }

    fun saveMemberId(memberId: String, context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.putString("memberId", memberId).apply()
    }

    fun logOut(context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.remove("token")
        editor.remove("name")
        editor.remove("status")
        editor.remove("memberId")
        editor.apply()
    }
}