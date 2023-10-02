package com.assignments.geektrust.di

import com.assignments.geektrust.repository.FalconRepository
import com.assignments.geektrust.repository.FalconRepositoryImpl
import com.assignments.geektrust.repository.service.FalconService
import com.assignments.geektrust.utils.Constants
import com.assignments.geektrust.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvider {

    @Provides
    @Singleton
    fun provideFalconService(retrofit: Retrofit): FalconService {
        return retrofit.create(FalconService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpInterceptor(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(logger)
        return okHttpClient.build()
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProvider
    }
}


@Module
@InstallIn(SingletonComponent::class)
interface AppModuleBinder {

    @Binds
    @Singleton
    fun bindFalconRepository(falconRepositoryImpl: FalconRepositoryImpl): FalconRepository
}