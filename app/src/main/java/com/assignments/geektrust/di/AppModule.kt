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
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvider {

    @Provides
    fun provideFalconService(): FalconService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_API)
            .build()
            .create(FalconService::class.java)
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