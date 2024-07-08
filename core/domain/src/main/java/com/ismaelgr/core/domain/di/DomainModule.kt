package com.ismaelgr.core.domain.di

import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import com.ismaelgr.core.domain.repository.UsersRepository
import com.ismaelgr.core.domain.usecase.AddExampleCaseUseCase
import com.ismaelgr.core.domain.usecase.AddPermissionUseCase
import com.ismaelgr.core.domain.usecase.GetLawerCasesUseCase
import com.ismaelgr.core.domain.usecase.GetLawersWithAccessToGivenCaseUseCase
import com.ismaelgr.core.domain.usecase.GetRandomUserUseCase
import com.ismaelgr.core.domain.usecase.RemovePermissionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    
    @Provides
    fun provideAddPermissionUseCase(permissionsRepository: PermissionsRepository) = AddPermissionUseCase(permissionsRepository)
    
    @Provides
    fun provideGetLawerCasesUseCase(caseRepository: CaseRepository, permissionsRepository: PermissionsRepository) = GetLawerCasesUseCase(caseRepository, permissionsRepository)
    
    @Provides
    fun provideGetLawersWithAccessToGivenCaseUseCase(caseRepository: CaseRepository, permissionsRepository: PermissionsRepository, usersRepository: UsersRepository) = GetLawersWithAccessToGivenCaseUseCase(caseRepository, permissionsRepository, usersRepository)
    
    @Provides
    fun provideRemovePermissionUseCase(permissionsRepository: PermissionsRepository) = RemovePermissionUseCase(permissionsRepository)
    
    @Provides
    fun provideGetRandomUserUseCase(usersRepository: UsersRepository) = GetRandomUserUseCase(usersRepository)
    
    @Provides
    fun provideAddExampleCaseUseCase(caseRepository: CaseRepository) = AddExampleCaseUseCase(caseRepository)
}