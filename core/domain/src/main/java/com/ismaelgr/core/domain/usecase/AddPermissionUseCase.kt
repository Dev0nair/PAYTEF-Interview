package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.entity.ErrorType
import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.PermissionType
import com.ismaelgr.core.domain.repository.PermissionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddPermissionUseCase @Inject constructor(private val permissionsRepository: PermissionsRepository) {
    sealed class Result {
        data class Error(val errorType: ErrorType): Result()
        data object Success: Result()
    }
    
    operator fun invoke(permission: Permission): Flow<Result> = flow {
        if (permission.permissionType == PermissionType.BASIC) {
            // Before we add a new basic permission, we need to check there's no previous permission given. This is necessary to not duplicate permissions, so if there's a basic permission already given, we'll send an error
            val basicPermissionAlreadyGiven = permissionsRepository.getAllPermission().mapNotNull { permissionId -> permissionsRepository.getPermission(permissionId) }.any { currentPermission -> currentPermission.permissionType == PermissionType.BASIC }
            if(basicPermissionAlreadyGiven) {
                emit(Result.Error(ErrorType.BASIC_PERMISSION_ALREADY_GIVEN))
                return@flow
            }
        }
        
        permissionsRepository.addPermission(permission)
        emit(Result.Success)
    }
    
}