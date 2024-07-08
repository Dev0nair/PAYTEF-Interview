package com.ismaelgr.core.data.di

import com.ismaelgr.core.data.datasource.DataSource
import com.ismaelgr.core.data.repository.RepositoryImpl
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import com.ismaelgr.core.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideDataSource(): DataSource = DataSource()
    
    @Provides
    fun provideRepository(dataSource: DataSource): RepositoryImpl = RepositoryImpl(dataSource)
    
    @Provides
    fun provideCasesRepository(repositoryImpl: RepositoryImpl): CaseRepository = repositoryImpl
    
    @Provides
    fun providePermissionsRepository(repositoryImpl: RepositoryImpl): PermissionsRepository = repositoryImpl
    
    @Provides
    fun provideUsersRepository(repositoryImpl: RepositoryImpl): UsersRepository = repositoryImpl
}