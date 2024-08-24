package com.course.dicodingstory.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.model.RemoteDataSource
import com.course.dicodingstory.model.RemoteDataSourceImpl
import com.course.dicodingstory.model.RemoteService
import com.course.dicodingstory.model.RepositoryStoryDicoding
import com.course.dicodingstory.model.RepositoryStoryDicodingImpl
import com.course.dicodingstory.model.StoryPagingSource
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 *hrahm,19/07/2024, 19:45
 **/
object AppModule {
    private const val MAIN_PREFS_LOCAL = "MAIN_PREFS_LOCAL"
    private fun provideSettingsPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(MAIN_PREFS_LOCAL, Context.MODE_PRIVATE)



    val remoteService = module {
        single { RemoteService.invoke(get()) }
    }


    val remoteModule = module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
        single { StoryPagingSource(get()) }
    }
    val repositoryModule = module {
        single<RepositoryStoryDicoding> { RepositoryStoryDicodingImpl(get()) }
    }
    val viewModelModule = module {
        viewModelOf(::RegisterViewModel)
    }
    val module: List<Module> =
        listOf(
            remoteService,
            remoteModule,
            repositoryModule,
            viewModelModule
        )
}