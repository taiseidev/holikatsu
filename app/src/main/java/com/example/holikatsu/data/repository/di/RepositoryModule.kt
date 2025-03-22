package com.example.holikatsu.data.repository.di

import com.example.holikatsu.data.datasource.remote.RemoteDataSource
import com.example.holikatsu.data.repository.HolidayRepositoryImpl
import com.example.holikatsu.domain.repository.HolidayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideHolidayRepository(
        remoteDataSource: RemoteDataSource
    ): HolidayRepository {
        return HolidayRepositoryImpl(remoteDataSource)
    }
}