package com.course.dicodingstory.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.course.dicodingstory.Register.RegisterViewModel
import com.course.dicodingstory.model.RemoteDataSource
import com.course.dicodingstory.model.RemoteDataSourceImpl
import com.course.dicodingstory.model.RemoteService
import com.course.dicodingstory.model.RepositoryStoryDicoding
import com.course.dicodingstory.model.RepositoryStoryDicodingImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 *hrahm,19/07/2024, 19:45
 **/
object AppModule {
    val remoteService = module {
        single { ChuckerInterceptor(androidContext()) }
        single { RemoteService.invoke(get()) }
    }

    val remoteModule = module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
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