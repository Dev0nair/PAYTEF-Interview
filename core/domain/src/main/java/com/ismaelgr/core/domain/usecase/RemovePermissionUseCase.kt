package com.ismaelgr.core.domain.usecase

import com.ismaelgr.core.domain.repository.PermissionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class RemovePermissionUseCase @Inject constructor(private val permissionsRepository: PermissionsRepository) {
    
    operator fun invoke(id: UUID): Flow<Unit> = flow {
        permissionsRepository.removePermission(id)
    }
    
}