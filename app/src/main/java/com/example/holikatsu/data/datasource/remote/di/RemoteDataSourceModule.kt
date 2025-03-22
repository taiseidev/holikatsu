package com.example.holikatsu.data.datasource.remote.di

import com.example.holikatsu.data.datasource.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    fun provideRemoteDataSourceModule(
        httpClient: HttpClient,
    ): RemoteDataSource {
        return RemoteDataSource(httpClient)
    }
}